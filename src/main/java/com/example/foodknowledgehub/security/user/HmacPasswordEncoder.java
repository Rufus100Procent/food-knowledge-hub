package com.example.foodknowledgehub.security.user;

import com.example.foodknowledgehub.utils.HashService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HmacPasswordEncoder implements PasswordEncoder {

    private final HashService hashService;

    public HmacPasswordEncoder(HashService hashService) {
        this.hashService = hashService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        return hashService.hash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        String candidate = hashService.hash(rawPassword.toString());
        return candidate.equals(encodedPassword);
    }
}

