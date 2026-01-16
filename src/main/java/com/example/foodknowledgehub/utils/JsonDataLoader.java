package com.example.foodknowledgehub.utils;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class JsonDataLoader {

    private JsonDataLoader() {
    }

    public static <T> List<T> loadList(
            ObjectMapper objectMapper,
            String fileName,
            TypeReference<List<T>> typeReference
    ) {
        String path = "/data/" + fileName;

        try (InputStream inputStream = JsonDataLoader.class.getResourceAsStream(path)) {

            if (inputStream == null) {
                throw new IllegalStateException(fileName + " not found on classpath at " + path);
            }

            return objectMapper.readValue(inputStream, typeReference);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load JSON file: " + fileName, e);
        }
    }
}
