package com.junlin.timeregy.data.utility;

import androidx.room.TypeConverter;

import com.junlin.timeregy.data.enums.Interruptions;

public class InterruptionsConverter {
    @TypeConverter
    public static Interruptions toInterruptions (Integer integer) {
        if (integer == null) return null;
        return Interruptions.toInteruptions(integer);
    }

    @TypeConverter
    public static Integer toInt(Interruptions interruptions) {
        return interruptions == null ? null : interruptions.getValue();
    }
}
