package com.example.foodknowledgehub.dto;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import com.example.foodknowledgehub.modal.miniral.Micromineral;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MicromineralAmountDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_micromineralAmountDto_passes_validation() {
        MicromineralAmountDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void null_micromineral_fails_validation() {
        MicromineralAmountDto dto = validDto();
        dto.setMicromineral(null);

        Set<ConstraintViolation<MicromineralAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "micromineral", NotNull.class);
    }

    @Test
    void negative_amountMilligrams_fails_validation() {
        MicromineralAmountDto dto = validDto();
        dto.setAmountMilligrams(-5.0);

        Set<ConstraintViolation<MicromineralAmountDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "amountMilligrams", DecimalMin.class);
    }

    private MicromineralAmountDto validDto() {
        MicromineralAmountDto dto = new MicromineralAmountDto();
        dto.setMicromineral(Micromineral.IRON);
        dto.setAmountMilligrams(18.0);
        dto.setDailyValuePercent(100.0);
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<MicromineralAmountDto>> violations,
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
