package com.junlin.timeregy.data.utility;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    // This class is referencing: https://www.youtube.com/watch?v=uuYm8FiO4Vg&feature=emb_logo
    @TypeConverter
    public static Date toDate(Long sqlDate) {
        if (sqlDate == null) return null;
        return new Date(sqlDate);
    }

    @TypeConverter
    public static Long toSqlDate(Date date) {
        if (date == null) return null;
        return date.getTime();
    }
}
