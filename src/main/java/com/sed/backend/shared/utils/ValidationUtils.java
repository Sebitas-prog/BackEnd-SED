package com.sed.backend.shared.utils;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}