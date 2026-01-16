package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.MicromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.Micromineral;
import com.example.foodknowledgehub.modal.miniral.MicromineralInfo;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.MicromineralInfoRepository;
import com.example.foodknowledgehub.service.mapper.MicromineralInfoMapper;
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

import static com.example.foodknowledgehub.utils.EmptyUtils.*;
import static com.example.foodknowledgehub.utils.EmptyUtils.isEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isListEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isListNotEmpty;
import static com.example.foodknowledgehub.utils.EmptyUtils.isNotEmpty;

@Service
@Transactional
public class MicromineralInfoService {

    private final Logger log = LoggerFactory.getLogger(MicromineralInfoService.class);

    private final MicromineralInfoRepository infoRepository;
    private final ObjectMapper objectMapper;
    private final FoodRepository foodRepository;
    private final MicromineralInfoMapper mapper;

    public MicromineralInfoService(
            MicromineralInfoRepository infoRepository, ObjectMapper objectMapper,
            FoodRepository foodRepository,
            MicromineralInfoMapper mapper
    ) {
        this.infoRepository = infoRepository;
        this.objectMapper = objectMapper;
        this.foodRepository = foodRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public MicromineralInfoDto getMicromineralDetail(String name) {
        Micromineral micromineral;
        try {
            micromineral = Micromineral.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown micromineral: " + name);
        }

        MicromineralInfo info = infoRepository.findByMicromineral(micromineral)
                .orElseThrow(() -> new IllegalArgumentException("No info for micromineral: " + name));

        List<Food> foods = foodRepository.findDistinctByMicrominerals_Micromineral(micromineral);

        return mapper.toDto(info, foods);
    }

    @Transactional(readOnly = true)
    public List<MicromineralInfoDto> getAll() {
        List<MicromineralInfo> entities = infoRepository.findAll();
        List<MicromineralInfoDto> dtos = new ArrayList<>();
        for (MicromineralInfo info : entities) {
            dtos.add(mapper.toDto(info, null));
        }
        return dtos;
    }

    @Transactional
    public void createBulkInsertOrMerge() {
        List<MicromineralInfo> jsonData = loadFromJson();

        for (MicromineralInfo jsonEntry : jsonData) {
            if (jsonEntry.getMicromineral() == null) {
                log.warn("Skipping JSON entry with null vitamin");
                continue;
            }

            Optional<MicromineralInfo> existingEntry =
                    infoRepository.findByMicromineral(jsonEntry.getMicromineral());

            if (existingEntry.isPresent()) {
                // Merge missing columns if the entry exists
                MicromineralInfo dbEntry = existingEntry.get();
                boolean updated = fillMissingColumns(dbEntry, jsonEntry);

                if (updated) {
                    infoRepository.save(dbEntry);
                    log.info("Updated {} with missing columns", dbEntry.getMicromineral());
                }
            } else {
                infoRepository.save(jsonEntry);
                log.info("Inserted new Vitamin: {}", jsonEntry.getMicromineral());
            }
        }

        log.info("Vitamin merge completed");
    }


    /**
     * Fills missing columns in the database table with data from JSON file.
     * Only updates columns that are null or empty in the database.
     */
    private boolean fillMissingColumns(MicromineralInfo dbEntry, MicromineralInfo jsonEntry) {
        boolean anyColumnUpdated = false;

        // Check and fill overview column
        if (isEmpty(dbEntry.getOverview()) && isNotEmpty(jsonEntry.getOverview())) {
            dbEntry.setOverview(jsonEntry.getOverview());
            anyColumnUpdated = true;
            log.debug("Filled overview for {}", dbEntry.getMicromineral());
        }

        // Check and fill benefits column
        if (isListEmpty(dbEntry.getBenefits()) && isListNotEmpty(jsonEntry.getBenefits())) {
            dbEntry.setBenefits(new ArrayList<>(jsonEntry.getBenefits()));
            anyColumnUpdated = true;
            log.debug("Filled benefits for {}", dbEntry.getMicromineral());
        }

        // Check and fill sideEffects column
        if (isListEmpty(dbEntry.getSideEffects()) && isListNotEmpty(jsonEntry.getSideEffects())) {
            dbEntry.setSideEffects(new ArrayList<>(jsonEntry.getSideEffects()));
            anyColumnUpdated = true;
            log.debug("Filled sideEffects for {}", dbEntry.getMicromineral());
        }

        // Check and fill deficiencySigns column
        if (isListEmpty(dbEntry.getDeficiencySigns()) && isListNotEmpty(jsonEntry.getDeficiencySigns())) {
            dbEntry.setDeficiencySigns(new ArrayList<>(jsonEntry.getDeficiencySigns()));
            anyColumnUpdated = true;
            log.debug("Filled deficiencySigns for {}", dbEntry.getMicromineral());
        }

        if (isEmpty(dbEntry.getImageUrl()) && isNotEmpty(jsonEntry.getImageUrl())) {
            dbEntry.setImageUrl(jsonEntry.getImageUrl());
            anyColumnUpdated = true;
            log.debug("Filled imageUrl for {}", dbEntry.getMicromineral());
        }

        return anyColumnUpdated;
    }

    private List<MicromineralInfo> loadFromJson() {
        return JsonDataLoader.loadList(
                objectMapper,
                "microminerals.json",
                new TypeReference<>() {
                }
        );
    }


}
