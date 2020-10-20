package com.junlin.timeregy.data.utility;

import androidx.room.TypeConverter;

import com.junlin.timeregy.data.enums.Tags;

public class TagsConverter {
    @TypeConverter
    public static Tags toTags (Integer integer) {
        if (integer == null) return null;
        return Tags.toTag(integer);
    }

    @TypeConverter
    public static Integer toInt(Tags tags) {
        return tags == null ? null : tags.getValue();
    }
}
