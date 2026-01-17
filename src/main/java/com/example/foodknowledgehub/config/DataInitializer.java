package com.example.foodknowledgehub.config;

import com.example.foodknowledgehub.service.MacromineralInfoService;
import com.example.foodknowledgehub.service.MicromineralInfoService;
import com.example.foodknowledgehub.service.VitaminInfoService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Transactional
@Component
public class DataInitializer implements ApplicationRunner {

    private final MacromineralInfoService macromineralInfoService;
    private final MicromineralInfoService micromineralInfoService;
    private final VitaminInfoService vitaminInfoService;

    public DataInitializer(MacromineralInfoService macromineralInfoService, MicromineralInfoService micromineralInfoService, VitaminInfoService vitaminInfoService) {
        this.macromineralInfoService = macromineralInfoService;
        this.micromineralInfoService = micromineralInfoService;
        this.vitaminInfoService = vitaminInfoService;
    }

    @Override
    public void run(@NonNull ApplicationArguments args){

        macromineralInfoService.createBulkInsertOrMerge();
        micromineralInfoService.createBulkInsertOrMerge();
        vitaminInfoService.createBulkInsertOrMerge();

    }
}
