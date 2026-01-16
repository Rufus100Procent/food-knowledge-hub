package com.example.foodknowledgehub.utils;

import java.util.List;

public final class  EmptyUtils {

    private EmptyUtils() {

    }
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isListEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isListNotEmpty(List<?> list) {
        return !isListEmpty(list);
    }

}
