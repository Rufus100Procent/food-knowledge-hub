package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_foodDto_passes_validation() {
        FoodDto dto = validFoodDto();

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void blank_name_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setName("");

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "name", NotBlank.class);
    }

    @Test
    void name_too_long_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setName("a".repeat(100));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "name", Size.class);
    }

    @Test
    void imageUrl_too_long_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setImageUrl("a".repeat(3000));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "imageUrl", Size.class);
    }

    @Test
    void overview_too_long_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setOverview("a".repeat(5000));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "overview", Size.class);
    }

    @Test
    void benefit_item_blank_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setBenefits(List.of(""));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "benefits", NotBlank.class);
    }

    @Test
    void benefit_item_too_long_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setBenefits(List.of("a".repeat(300)));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "benefits", Size.class);
    }

    @Test
    void too_many_benefits_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setBenefits(Collections.nCopies(60, "ok"));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "benefits", Size.class);
    }

    @Test
    void negative_calories_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.getMacronutrients().setCalories(-1.1);

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "macronutrients.calories", DecimalMin.class);
    }

    @Test
    void too_many_macrominerals_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setMacrominerals(Collections.nCopies(60, new MacromineralAmountDto()));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "macrominerals", Size.class);
    }

    @Test
    void too_many_microminerals_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setMicrominerals(Collections.nCopies(60, new MicromineralAmountDto()));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "microminerals", Size.class);
    }

    @Test
    void too_many_vitamins_fails_validation() {
        FoodDto dto = validFoodDto();
        dto.setVitamins(Collections.nCopies(60, new VitaminAmountDto()));

        Set<ConstraintViolation<FoodDto>> violations = validator.validate(dto);

        assertHasViolation(violations, "vitamins", Size.class);
    }

    private void assertHasViolation(
            Set<ConstraintViolation<FoodDto>> violations,
            String field,
            Class<?> annotationType
    ) {
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().startsWith(field) &&
                                v.getConstraintDescriptor()
                                        .getAnnotation()
                                        .annotationType()
                                        .equals(annotationType)
                ),
                "Expected violation on field '" + field +
                        "' with constraint @" + annotationType.getSimpleName()
        );
    }

    private FoodDto validFoodDto() {
        FoodDto dto = new FoodDto();
        dto.setName("Banana");
        dto.setImageUrl("https://example.com/banana.png");
        dto.setOverview("Yellow potassium stick.");
        dto.setBenefits(List.of("High in potassium"));

        MacronutrientProfileDto macros = new MacronutrientProfileDto();
        macros.setCalories(8.90);
        macros.setProteinGrams(1.1);
        macros.setFatGrams(0.3);
        macros.setCarbohydratesGrams(23.2);
        macros.setFiberGrams(2.6);
        macros.setSugarGrams(12.1);

        dto.setMacronutrients(macros);

        return dto;
    }
}
