package com.example.foodknowledgehub.service;

import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.Macromineral;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import com.example.foodknowledgehub.repo.FoodRepository;
import com.example.foodknowledgehub.repo.MacromineralInfoRepository;
import com.example.foodknowledgehub.service.mapper.MacromineralInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MacromineralInfoService {

    private final MacromineralInfoRepository infoRepository;
    private final FoodRepository foodRepository;
    private final MacromineralInfoMapper mapper;

    public MacromineralInfoService(
            MacromineralInfoRepository infoRepository,
            FoodRepository foodRepository,
            MacromineralInfoMapper mapper) {
        this.infoRepository = infoRepository;
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

    public List<MacromineralInfoDto> bulkUpsert(List<MacromineralInfoDto> dtos) {
        List<MacromineralInfoDto> result = new ArrayList<>();

        for (MacromineralInfoDto dto : dtos) {
            Macromineral macromineral =
                    Macromineral.valueOf(dto.getMacromineral().toUpperCase());

            MacromineralInfo entity = infoRepository.findByMacromineral(macromineral)
                            .orElseGet(() -> {
                                MacromineralInfo e = new MacromineralInfo();
                                e.setMacromineral(macromineral);
                                return e;
                            });

            mapper.applyDtoToEntity(dto, entity);
            result.add(mapper.toDto(infoRepository.save(entity), null));
        }

        return result;
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
}
