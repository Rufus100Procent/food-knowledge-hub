package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.VitaminInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.VitaminInfoRepository;
import com.example.foodknowledgehub.service.mapper.VitaminInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VitaminInfoService {

    private final VitaminInfoRepository infoRepository;
    private final FoodRepository foodRepository;
    private final VitaminInfoMapper mapper;

    public VitaminInfoService(
            VitaminInfoRepository infoRepository,
            FoodRepository foodRepository,
            VitaminInfoMapper mapper
    ) {
        this.infoRepository = infoRepository;
        this.foodRepository = foodRepository;
        this.mapper = mapper;
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
}
