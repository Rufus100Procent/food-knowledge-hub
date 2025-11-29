package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.MicromineralInfoDto;
import com.example.foodknowledgehub.service.MicromineralInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/0/microminerals")
public class MicromineralInfoController {

    private final MicromineralInfoService service;

    public MicromineralInfoController(MicromineralInfoService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public MicromineralInfoDto getMicromineral(@PathVariable String name) {
        return service.getMicromineralDetail(name);
    }
}
