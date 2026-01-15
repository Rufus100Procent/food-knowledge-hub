package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VitaminAmountDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_vitaminAmountDto_passes_validation() {
        VitaminAmountDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void null_vitamin_fails_validation() {
        VitaminAmountDto dto = validDto();
        dto.setVitamin(null);

        Set<ConstraintViolation<VitaminAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "vitamin", NotNull.class);
    }

    @Test
    void negative_amountMilligrams_fails_validation() {
        VitaminAmountDto dto = validDto();
        dto.setAmountMilligrams(-5.0);

        Set<ConstraintViolation<VitaminAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "amountMilligrams", DecimalMin.class);
    }

    @Test
    void dailyValuePercent_too_high_fails_validation() {
        VitaminAmountDto dto = validDto();
        dto.setDailyValuePercent(2000.0);

        Set<ConstraintViolation<VitaminAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "dailyValuePercent", DecimalMax.class);
    }

    private VitaminAmountDto validDto() {
        VitaminAmountDto dto = new VitaminAmountDto();
        dto.setVitamin(Vitamin.C);
        dto.setAmountMilligrams(75.0);
        dto.setDailyValuePercent(100.0);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<VitaminAmountDto>> violations,
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
