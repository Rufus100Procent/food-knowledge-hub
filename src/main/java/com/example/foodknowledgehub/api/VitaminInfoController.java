package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.VitaminInfoDto;
import com.example.foodknowledgehub.service.VitaminInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/vitamins")
public class VitaminInfoController {

    private final VitaminInfoService service;

    public VitaminInfoController(VitaminInfoService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public VitaminInfoDto getVitamin(@PathVariable String name) {
        return service.getVitaminDetail(name);
    }
}
