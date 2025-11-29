package com.example.foodknowledgehub.api.admin;

import com.example.foodknowledgehub.dto.MacromineralInfoDto;
import com.example.foodknowledgehub.service.MacromineralInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/admin/macrominerals")
public class MacromineralInfoAdminController {

    private final MacromineralInfoService service;

    public MacromineralInfoAdminController(MacromineralInfoService service) {
        this.service = service;
    }

    @PostMapping("/bulk")
    public List<MacromineralInfoDto> bulkUpsert(@RequestBody List<MacromineralInfoDto> body) {
        return service.bulkUpsert(body);
    }

    @GetMapping
    public List<MacromineralInfoDto> getAll() {
        return service.getAll();
    }
}
