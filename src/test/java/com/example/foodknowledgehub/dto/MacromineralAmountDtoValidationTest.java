package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import com.example.foodknowledgehub.modal.miniral.Macromineral;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MacromineralAmountDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_macromineralAmountDto_passes_validation() {
        MacromineralAmountDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void null_macromineral_fails_validation() {
        MacromineralAmountDto dto = validDto();
        dto.setMacromineral(null);

        Set<ConstraintViolation<MacromineralAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "macromineral", NotNull.class);
    }

    @Test
    void negative_amountMilligrams_fails_validation() {
        MacromineralAmountDto dto = validDto();
        dto.setAmountMilligrams(-1.0);

        Set<ConstraintViolation<MacromineralAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "amountMilligrams", DecimalMin.class);
    }

    @Test
    void dailyValuePercent_too_high_fails_validation() {
        MacromineralAmountDto dto = validDto();
        dto.setDailyValuePercent(2000.0);

        Set<ConstraintViolation<MacromineralAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "dailyValuePercent", DecimalMax.class);
    }

    private MacromineralAmountDto validDto() {
        MacromineralAmountDto dto = new MacromineralAmountDto();
        dto.setMacromineral(Macromineral.POTASSIUM);
        dto.setAmountMilligrams(350.0);
        dto.setDailyValuePercent(10.0);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<MacromineralAmountDto>> violations,
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
