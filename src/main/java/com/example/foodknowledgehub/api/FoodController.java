package com.example.foodknowledgehub.api;

import com.example.foodknowledgehub.dto.FoodDto;
import com.example.foodknowledgehub.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public ResponseEntity<FoodDto> createFood( @Valid @RequestBody FoodDto dto) {
        FoodDto created = foodService.createFood(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFood(@PathVariable Long id) {
        FoodDto food = foodService.getFood(id);
        return ResponseEntity.ok(food);
    }

    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        List<FoodDto> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable Long id, @RequestBody FoodDto dto) {
        FoodDto updated = foodService.updateFood(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @RestControllerAdvice
    public static class ValidationExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

            Map<String, Object> errors = new LinkedHashMap<>();

            errors.put("error", "Validation failed");

            List<Map<String, String>> details = new ArrayList<>();

            ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                Map<String, String> detail = new LinkedHashMap<>();
                detail.put("field", fieldError.getField());
                detail.put("message", fieldError.getDefaultMessage());
                details.add(detail);
            });

            errors.put("details", details);

            return ResponseEntity.badRequest().body(errors);
        }
    }

}
