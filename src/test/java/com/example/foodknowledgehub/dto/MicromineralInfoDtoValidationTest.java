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

class MicromineralInfoDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_micromineralInfo_passes_validation() {
        MicromineralInfoDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void overview_too_long_fails_validation() {
        MicromineralInfoDto dto = validDto();
        dto.setOverview("a".repeat(5000));

        Set<ConstraintViolation<MicromineralInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "overview", Size.class);
    }

    @Test
    void benefit_blank_fails_validation() {
        MicromineralInfoDto dto = validDto();
        dto.setBenefits(List.of(""));

        Set<ConstraintViolation<MicromineralInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "benefits", NotBlank.class);
    }

    @Test
    void too_many_deficiencySigns_fails_validation() {
        MicromineralInfoDto dto = validDto();
        dto.setDeficiencySigns(Collections.nCopies(60, "sign"));

        Set<ConstraintViolation<MicromineralInfoDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "deficiencySigns", Size.class);
    }

    private MicromineralInfoDto validDto() {
        MicromineralInfoDto dto = new MicromineralInfoDto();
        dto.setOverview("Trace mineral essential for health.");
        dto.setBenefits(List.of("Supports metabolism"));
        dto.setSideEffects(List.of("Excess may cause issues"));
        dto.setDeficiencySigns(List.of("Fatigue"));
        dto.setVerified(true);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<MicromineralInfoDto>> violations,
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
