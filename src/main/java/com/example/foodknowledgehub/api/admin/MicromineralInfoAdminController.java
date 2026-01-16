package com.example.foodknowledgehub.api.admin;

import com.example.foodknowledgehub.dto.MicromineralInfoDto;
import com.example.foodknowledgehub.service.MicromineralInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/admin/microminerals")
public class MicromineralInfoAdminController {

    private final MicromineralInfoService service;

    public MicromineralInfoAdminController(MicromineralInfoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MicromineralInfoDto> getAll() {
        return service.getAll();
    }
}
