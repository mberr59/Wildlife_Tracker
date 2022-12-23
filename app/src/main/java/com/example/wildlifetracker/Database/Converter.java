package com.example.wildlifetracker.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static java.util.Date toDate(Long timestamp) {
        return timestamp == null?null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(java.util.Date date) {
        return date == null?null : date.getTime();
    }
}
