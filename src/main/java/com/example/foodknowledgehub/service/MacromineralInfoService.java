package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.Macromineral;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.MacromineralInfoRepository;
import com.example.foodknowledgehub.service.mapper.MacromineralInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.foodknowledgehub.utils.EmptyUtils.*;

@Service
@Transactional
public class MacromineralInfoService {

    private final Logger log = LoggerFactory.getLogger(MacromineralInfoService.class);

    private final MacromineralInfoRepository infoRepository;
    private final ObjectMapper objectMapper;
    private final FoodRepository foodRepository;
    private final MacromineralInfoMapper mapper;

    public MacromineralInfoService(
            MacromineralInfoRepository infoRepository, ObjectMapper objectMapper,
            FoodRepository foodRepository,
            MacromineralInfoMapper mapper) {
        this.infoRepository = infoRepository;
        this.objectMapper = objectMapper;
        this.foodRepository = foodRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public MacromineralInfoDto getMacromineralDetail(String name) {
        Macromineral macromineral = Macromineral.valueOf(name.toUpperCase());

        MacromineralInfo info = infoRepository.findByMacromineral(macromineral)
                .orElseThrow(() -> new IllegalArgumentException("No info for macromineral"));

        List<Food> foods =
                foodRepository.findDistinctByMacrominerals_Macromineral(macromineral);

        return mapper.toDto(info, foods);
    }


    @Transactional(readOnly = true)
    public List<MacromineralInfoDto> getAll() {

        List<MacromineralInfo> entities = infoRepository.findAll();
        List<MacromineralInfoDto> dos = new ArrayList<>();
        for (MacromineralInfo info : entities) {
            dos.add(mapper.toDto(info, null));
        }
//
//        return infoRepository.findAll()
//                .stream()
//                .map(e -> mapper.toDto(e, null))
//                .toList();
        return dos;

    }

    @Transactional
    public void createBulkInsertOrMerge() {
        List<MacromineralInfo> jsonData = loadFromJson();

        for (MacromineralInfo jsonEntry : jsonData) {
            if (jsonEntry.getMacromineral() == null) {
                log.warn("Skipping JSON entry with null macromineral");
                continue;
            }

            Optional<MacromineralInfo> existingEntry =
                    infoRepository.findByMacromineral(jsonEntry.getMacromineral());

            if (existingEntry.isPresent()) {
                // Merge missing columns if the entry exists
                MacromineralInfo dbEntry = existingEntry.get();
                boolean updated = fillMissingColumns(dbEntry, jsonEntry);

                if (updated) {
                    infoRepository.save(dbEntry);
                    log.info("Updated {} with missing columns", dbEntry.getMacromineral());
                }
            } else {
                infoRepository.save(jsonEntry);
                log.info("Inserted new macromineral: {}", jsonEntry.getMacromineral());
            }
        }

        log.info("Macromineral merge completed");
    }


    /**
     * Fills missing columns in the database table with data from JSON file.
     * Only updates columns that are null or empty in the database.
     */
    private boolean fillMissingColumns(MacromineralInfo dbEntry, MacromineralInfo jsonEntry) {
        boolean anyColumnUpdated = false;

        // Check and fill overview column
        if (isEmpty(dbEntry.getOverview()) && isNotEmpty(jsonEntry.getOverview())) {
            dbEntry.setOverview(jsonEntry.getOverview());
            anyColumnUpdated = true;
            log.debug("Filled overview for {}", dbEntry.getMacromineral());
        }

        // Check and fill benefits column
        if (isListEmpty(dbEntry.getBenefits()) && isListNotEmpty(jsonEntry.getBenefits())) {
            dbEntry.setBenefits(new ArrayList<>(jsonEntry.getBenefits()));
            anyColumnUpdated = true;
            log.debug("Filled benefits for {}", dbEntry.getMacromineral());
        }

        // Check and fill sideEffects column
        if (isListEmpty(dbEntry.getSideEffects()) && isListNotEmpty(jsonEntry.getSideEffects())) {
            dbEntry.setSideEffects(new ArrayList<>(jsonEntry.getSideEffects()));
            anyColumnUpdated = true;
            log.debug("Filled sideEffects for {}", dbEntry.getMacromineral());
        }

        // Check and fill deficiencySigns column
        if (isListEmpty(dbEntry.getDeficiencySigns()) && isListNotEmpty(jsonEntry.getDeficiencySigns())) {
            dbEntry.setDeficiencySigns(new ArrayList<>(jsonEntry.getDeficiencySigns()));
            anyColumnUpdated = true;
            log.debug("Filled deficiencySigns for {}", dbEntry.getMacromineral());
        }

        if (isEmpty(dbEntry.getImageUrl()) && isNotEmpty(jsonEntry.getImageUrl())) {
            dbEntry.setImageUrl(jsonEntry.getImageUrl());
            anyColumnUpdated = true;
            log.debug("Filled imageUrl for {}", dbEntry.getMacromineral());
        }

        return anyColumnUpdated;
    }

    private List<MacromineralInfo> loadFromJson() {
        try (InputStream macroMinstrelsJsonFile = getClass().getResourceAsStream("/data/macrominerals.json")) {
            if (macroMinstrelsJsonFile == null) {
                throw new IllegalStateException("macrominerals.json not found in classpath");
            }

            return objectMapper.readValue(macroMinstrelsJsonFile, new TypeReference<>() {
            });

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load macrominerals.json", e);
        }
    }

}
