package com.github.kaydunov.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

public class DateConverter {

    private DateConverter() {
    }

    /**
     * Converts a year to a Timestamp, setting the date to January 1st of the specified year.
     *
     * @param year The year to convert.
     * @return Timestamp corresponding to the start of the specified year.
     */
    public static Timestamp toTimestamp(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        return Timestamp.valueOf(date.atStartOfDay());
    }

    /**
     * Converts a year and month to a Timestamp, setting the date to the 1st day of the specified month.
     *
     * @param year  The year to convert.
     * @param month The month to convert.
     * @return Timestamp corresponding to the start of the specified month in the specified year.
     */
    public static Timestamp toTimestamp(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return Timestamp.valueOf(date.atStartOfDay());
    }

    /**
     * Returns the current time as a Timestamp.
     *
     * @return The current Timestamp.
     */
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.from(Instant.now());
    }
}
