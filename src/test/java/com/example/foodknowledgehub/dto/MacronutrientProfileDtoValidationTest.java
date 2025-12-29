package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MacronutrientProfileDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_macronutrientProfile_passes_validation() {
        MacronutrientProfileDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void negative_calories_fails_validation() {
        MacronutrientProfileDto dto = validDto();
        dto.setCalories(-1);

        Set<ConstraintViolation<MacronutrientProfileDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "calories", DecimalMin.class);
    }

    @Test
    void protein_too_high_fails_validation() {
        MacronutrientProfileDto dto = validDto();
        dto.setProteinGrams(50000);

        Set<ConstraintViolation<MacronutrientProfileDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "proteinGrams", DecimalMax.class);
    }

    private MacronutrientProfileDto validDto() {
        MacronutrientProfileDto dto = new MacronutrientProfileDto();
        dto.setCalories(500);
        dto.setProteinGrams(25);
        dto.setFatGrams(10);
        dto.setCarbohydratesGrams(60);
        dto.setFiberGrams(8);
        dto.setSugarGrams(5);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<MacronutrientProfileDto>> violations,
            String field,
            Class<?> annotation
    ) {
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals(field) &&
                                v.getConstraintDescriptor()
                                        .getAnnotation()
                                        .annotationType()
                                        .equals(annotation)
                )
        );
    }
}
