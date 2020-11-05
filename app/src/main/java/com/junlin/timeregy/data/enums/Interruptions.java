package com.junlin.timeregy.data.enums;

import java.util.HashMap;

public enum Interruptions {
    KEEPRUNNING(0),
    PAUSETIMER(1),
    CANCELTIMER(2),
    UNKNOWN(-1);

    private final Integer value;


    private static final HashMap<Integer, Interruptions> intToInterruptions = new HashMap<>();
    static {
        for (Interruptions interruption : Interruptions.values()) {
            intToInterruptions.put(interruption.value, interruption);
        }
    }

    public static Interruptions toInteruptions (Integer num) {
        Interruptions interruptions = intToInterruptions.get(num);
        return interruptions == null ? UNKNOWN : interruptions;
    }

    Interruptions(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
