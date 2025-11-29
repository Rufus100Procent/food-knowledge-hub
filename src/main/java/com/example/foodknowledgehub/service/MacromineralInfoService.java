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
            MacromineralInfoMapper mapper
    ) {
        this.infoRepository = infoRepository;
        this.foodRepository = foodRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public MacromineralInfoDto getMacromineralDetail(String name) {
        Macromineral macromineral;
        try {
            macromineral = Macromineral.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown macromineral: " + name);
        }

        MacromineralInfo info = infoRepository.findByMacromineral(macromineral)
                .orElseThrow(() -> new IllegalArgumentException("No info for macromineral: " + name));

        List<Food> foods = foodRepository.findDistinctByMacrominerals_Macromineral(macromineral);

        return mapper.toDto(info, foods);
    }

    public List<MacromineralInfoDto> bulkUpsert(List<MacromineralInfoDto> dos) {
        List<MacromineralInfoDto> result = new ArrayList<>();

        for (MacromineralInfoDto dto : dos) {
            if (dto.getMacromineral() == null) {
                continue;
            }

            Macromineral macromineral = Macromineral.valueOf(dto.getMacromineral().toUpperCase());

            MacromineralInfo entity = infoRepository.findByMacromineral(macromineral)
                    .orElseGet(() -> {
                        MacromineralInfo created = new MacromineralInfo();
                        created.setMacromineral(macromineral);
                        return created;
                    });

            mapper.applyDtoToEntity(dto, entity);

            MacromineralInfo saved = infoRepository.save(entity);

            // for bulk admin no  need for foods list,  pass null
            result.add(mapper.toDto(saved, null));
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
        return dos;
    }
}
