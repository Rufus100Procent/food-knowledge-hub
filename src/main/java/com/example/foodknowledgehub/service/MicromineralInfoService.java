package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.MicromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.Micromineral;
import com.example.foodknowledgehub.modal.miniral.MicromineralInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.MicromineralInfoRepository;
import com.example.foodknowledgehub.service.mapper.MicromineralInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MicromineralInfoService {

    private final MicromineralInfoRepository infoRepository;
    private final FoodRepository foodRepository;
    private final MicromineralInfoMapper mapper;

    public MicromineralInfoService(
            MicromineralInfoRepository infoRepository,
            FoodRepository foodRepository,
            MicromineralInfoMapper mapper
    ) {
        this.infoRepository = infoRepository;
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
}
