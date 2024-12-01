package com.bezkoder.springjwt.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtils {

    public static Date convertMillisToDate(long millis) {
        return new Date(millis);
    }

    public static LocalDateTime convertMillisToLocalDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        // Format the LocalDateTime as desired
        return localDateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getCurrentDateTimeString() {
        return formatLocalDateTime(LocalDateTime.now());
    }
}