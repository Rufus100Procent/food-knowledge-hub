package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.service.MacromineralInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/macrominerals")
public class MacromineralInfoController {

    private final MacromineralInfoService service;

    public MacromineralInfoController(MacromineralInfoService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public MacromineralInfoDto getMacromineral(@PathVariable String name) {
        return service.getMacromineralDetail(name);
    }
}
