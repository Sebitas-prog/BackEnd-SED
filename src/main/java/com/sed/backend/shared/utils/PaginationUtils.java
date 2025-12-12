package com.sed.backend.shared.utils;

public final class PaginationUtils {
    private PaginationUtils() {
    }

    public static int sanitizePage(int page) {
        return Math.max(page, 0);
    }

    public static int sanitizeSize(int size, int max) {
        return Math.min(Math.max(size, 1), max);
    }
}