package com.forthwall.cinema.movie.utils;

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtils {


    public static java.time.format.DateTimeFormatter getDateTimeFormatter(){
        java.time.format.DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();
        return formatter;
    }
}
