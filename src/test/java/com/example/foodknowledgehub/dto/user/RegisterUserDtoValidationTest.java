package com.example.foodknowledgehub.dto.user;

import com.example.foodknowledgehub.dto.validation.TestValidators;
import com.example.foodknowledgehub.security.user.dto.RegisterUserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterUserDtoValidationTest {

    private final Validator validator = TestValidators.validator();

    @Test
    void valid_registerUserDto_passes_validation() {
        RegisterUserDto dto = validDto();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void blank_fullName_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setFullName("");

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "fullName", NotBlank.class);
    }

    @Test
    void fullName_too_long_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setFullName("a".repeat(100));

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "fullName", Size.class);
    }

    @Test
    void blank_userName_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setUserName("");

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "userName", NotBlank.class);
    }

    @Test
    void userName_too_long_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setUserName("a".repeat(100));

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "userName", Size.class);
    }

    @Test
    void blank_password_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setPassword("");

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "password", NotBlank.class);
    }

    @Test
    void password_too_short_fails_validation() {
        RegisterUserDto dto = validDto();
        dto.setPassword("short");

        Set<ConstraintViolation<RegisterUserDto>> violations =
                validator.validate(dto);

        assertHasViolation(violations, "password", Size.class);
    }

    private RegisterUserDto validDto() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("John Doe");
        dto.setUserName("johndoe");
        dto.setPassword("strongPassword123");
        return dto;
    }

    private void assertHasViolation(
            Set<ConstraintViolation<RegisterUserDto>> violations,
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
                ),
                "Expected violation on field '" + field +
                        "' with constraint @" + annotation.getSimpleName()
        );
    }

}
