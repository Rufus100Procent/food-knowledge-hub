package com.example.foodknowledgehub.utils;

import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JsonDataLoaderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loadList_readsJsonFromTestClasspath() {
        List<TestDto> result = JsonDataLoader.loadList(
                objectMapper,
                "vitamins-test.json",
                new TypeReference<>() {
                }
        );

        assertEquals(3, result.size());
        assertEquals("A", result.get(0).vitamin());
        assertEquals("B1_THIAMINE", result.get(1).vitamin());
        assertEquals("B5_PANTOTHENIC_ACID", result.get(2).vitamin());
    }

    @Test
    void loadList_deserializesEmptyArray() {
        List<TestDto> result = JsonDataLoader.loadList(
                objectMapper,
                "empty.json",
                new TypeReference<>() {
                }
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void loadList_throwsException_whenFileDoesNotExist() {
        assertThrows(
                IllegalStateException.class,
                () -> JsonDataLoader.loadList(
                        objectMapper,
                        "does-not-exist.json",
                        new TypeReference<List<TestDto>>() {}
                )
        );
    }

    record TestDto(String vitamin) {}
}
