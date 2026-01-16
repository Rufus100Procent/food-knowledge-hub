package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.VitaminInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.VitaminInfoRepository;
import com.example.foodknowledgehub.service.mapper.VitaminInfoMapper;
import com.example.foodknowledgehub.utils.JsonDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.foodknowledgehub.utils.EmptyUtils.isEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isListEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isListNotEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isNotEmpty;

@Service
@Transactional
public class VitaminInfoService {

    private final Logger log = LoggerFactory.getLogger(VitaminInfoService.class);

    private final VitaminInfoRepository infoRepository;
    private final FoodRepository foodRepository;
    private final VitaminInfoMapper mapper;
    private final ObjectMapper objectMapper;
    public VitaminInfoService(
            VitaminInfoRepository infoRepository,
            FoodRepository foodRepository,
            VitaminInfoMapper mapper, ObjectMapper objectMapper
    ) {
        this.infoRepository = infoRepository;
        this.foodRepository = foodRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public VitaminInfoDto getVitaminDetail(String name) {
        Vitamin vitamin;
        try {
            vitamin = Vitamin.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown vitamin: " + name);
        }

        VitaminInfo info = infoRepository.findByVitamin(vitamin)
                .orElseThrow(() -> new IllegalArgumentException("No info for vitamin: " + name));

        List<Food> foods = foodRepository.findDistinctByVitamins_Vitamin(vitamin);

        return mapper.toDto(info, foods);
    }

    @Transactional(readOnly = true)
    public List<VitaminInfoDto> getAll() {
        List<VitaminInfo> entities = infoRepository.findAll();
        List<VitaminInfoDto> dtos = new ArrayList<>();
        for (VitaminInfo info : entities) {
            dtos.add(mapper.toDto(info, null));
        }
        return dtos;
    }

    @Transactional
    public void createBulkInsertOrMerge() {
        List<VitaminInfo> jsonData = loadFromJson();

        for (VitaminInfo jsonEntry : jsonData) {
            if (jsonEntry.getVitamin() == null) {
                log.warn("Skipping JSON entry with null vitamin");
                continue;
            }

            Optional<VitaminInfo> existingEntry =
                    infoRepository.findByVitamin(jsonEntry.getVitamin());

            if (existingEntry.isPresent()) {
                // Merge missing columns if the entry exists
                VitaminInfo dbEntry = existingEntry.get();
                boolean updated = fillMissingColumns(dbEntry, jsonEntry);

                if (updated) {
                    infoRepository.save(dbEntry);
                    log.info("Updated {} with missing columns", dbEntry.getVitamin());
                }
            } else {
                infoRepository.save(jsonEntry);
                log.info("Inserted new Vitamin: {}", jsonEntry.getVitamin());
            }
        }

        log.info("Vitamin merge completed");
    }


    /**
     * Fills missing columns in the database table with data from JSON file.
     * Only updates columns that are null or empty in the database.
     */
    private boolean fillMissingColumns(VitaminInfo dbEntry, VitaminInfo jsonEntry) {
        boolean anyColumnUpdated = false;

        // Check and fill overview column
        if (isEmpty(dbEntry.getOverview()) && isNotEmpty(jsonEntry.getOverview())) {
            dbEntry.setOverview(jsonEntry.getOverview());
            anyColumnUpdated = true;
            log.debug("Filled overview for {}", dbEntry.getVitamin());
        }

        // Check and fill benefits column
        if (isListEmpty(dbEntry.getBenefits()) && isListNotEmpty(jsonEntry.getBenefits())) {
            dbEntry.setBenefits(new ArrayList<>(jsonEntry.getBenefits()));
            anyColumnUpdated = true;
            log.debug("Filled benefits for {}", dbEntry.getVitamin());
        }

        // Check and fill sideEffects column
        if (isListEmpty(dbEntry.getSideEffects()) && isListNotEmpty(jsonEntry.getSideEffects())) {
            dbEntry.setSideEffects(new ArrayList<>(jsonEntry.getSideEffects()));
            anyColumnUpdated = true;
            log.debug("Filled sideEffects for {}", dbEntry.getVitamin());
        }

        // Check and fill deficiencySigns column
        if (isListEmpty(dbEntry.getDeficiencySigns()) && isListNotEmpty(jsonEntry.getDeficiencySigns())) {
            dbEntry.setDeficiencySigns(new ArrayList<>(jsonEntry.getDeficiencySigns()));
            anyColumnUpdated = true;
            log.debug("Filled deficiencySigns for {}", dbEntry.getVitamin());
        }

        if (isEmpty(dbEntry.getImageUrl()) && isNotEmpty(jsonEntry.getImageUrl())) {
            dbEntry.setImageUrl(jsonEntry.getImageUrl());
            anyColumnUpdated = true;
            log.debug("Filled imageUrl for {}", dbEntry.getVitamin());
        }

        return anyColumnUpdated;
    }

    private List<VitaminInfo> loadFromJson() {
        return JsonDataLoader.loadList(
                objectMapper,
                "vitamins.json",
                new TypeReference<>() {
                }
        );
    }

}
