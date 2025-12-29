package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VitaminInfoDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_vitaminInfo_passes_validation() {
        VitaminInfoDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void overview_too_long_fails_validation() {
        VitaminInfoDto dto = validDto();
        dto.setOverview("a".repeat(5000));

        Set<ConstraintViolation<VitaminInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "overview", Size.class);
    }

    @Test
    void benefit_blank_fails_validation() {
        VitaminInfoDto dto = validDto();
        dto.setBenefits(List.of(""));

        Set<ConstraintViolation<VitaminInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "benefits", NotBlank.class);
    }

    @Test
    void too_many_sideEffects_fails_validation() {
        VitaminInfoDto dto = validDto();
        dto.setSideEffects(Collections.nCopies(60, "effect"));

        Set<ConstraintViolation<VitaminInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "sideEffects", Size.class);
    }

    private VitaminInfoDto validDto() {
        VitaminInfoDto dto = new VitaminInfoDto();
        dto.setOverview("Essential vitamin for health.");
        dto.setBenefits(List.of("Supports immune system"));
        dto.setSideEffects(List.of("Excess intake may cause issues"));
        dto.setDeficiencySigns(List.of("Fatigue"));
        dto.setVerified(true);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<VitaminInfoDto>> violations,
            String field,
            Class<?> annotation
    ) {
        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().startsWith(field) &&
                                v.getConstraintDescriptor()
                                        .getAnnotation()
                                        .annotationType()
                                        .equals(annotation)
                )
        );
    }


}
