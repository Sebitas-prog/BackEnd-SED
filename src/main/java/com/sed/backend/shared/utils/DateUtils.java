package com.sed.backend.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    private DateUtils() {
    }

    public static String formatIso(LocalDateTime date) {
        return date != null ? date.format(DateTimeFormatter.ISO_DATE_TIME) : null;
    }
}