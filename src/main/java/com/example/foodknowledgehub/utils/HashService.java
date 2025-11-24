package com.example.foodknowledgehub.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;

@Component
public class HashService {
    private static final String ALG = "HmacSHA256";
    private final SecretKeySpec key;

    public HashService(@Value("${hash.pepper.base64}") String pepperB64) {
        if (pepperB64 == null || pepperB64.isBlank()) {
            throw new IllegalStateException("hash.pepper.base64 missing");
        }
        byte[] raw;
        try { raw = Base64.getDecoder().decode(pepperB64); }
        catch (IllegalArgumentException e) { throw new IllegalStateException("Pepper must be Base64", e); }
        if (raw.length < 32) {
            throw new IllegalStateException("Pepper must be >= 32 bytes");
        }
        try { this.key = new SecretKeySpec(raw, ALG); }
        finally { Arrays.fill(raw, (byte) 0); }
    }

    public String hash(String value) {
        if (value == null) return null;
        try {
            Mac mac = Mac.getInstance(ALG);
            mac.init(key);
            byte[] tag = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(tag);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("HMAC failed", e);
        }
    }
}
