package com.github.kaydunov.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeStampConverter {

    private TimeStampConverter() {
    }
    
    public static Timestamp convertYearToTimestamp(int year) {
        // Define the start date and time for the specified year, e.g., January 1st at 00:00
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        return Timestamp.valueOf(startOfYear);
    }
}
