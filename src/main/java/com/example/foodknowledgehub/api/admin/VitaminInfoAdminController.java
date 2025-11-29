package com.example.foodknowledgehub.api.admin;

import com.example.foodknowledgehub.dto.VitaminInfoDto;
import com.example.foodknowledgehub.service.VitaminInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/admin/vitamins")
public class VitaminInfoAdminController {

    private final VitaminInfoService service;

    public VitaminInfoAdminController(VitaminInfoService service) {
        this.service = service;
    }

    @PostMapping("/bulk")
    public List<VitaminInfoDto> bulkUpsert(@RequestBody List<VitaminInfoDto> body) {
        return service.bulkUpsert(body);
    }

    @GetMapping
    public List<VitaminInfoDto> getAll() {
        return service.getAll();
    }
}
