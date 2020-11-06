package com.junlin.timeregy.data.utility;

public class TimeConverter {
    public static int stringToSeconds(String minutes, String seconds) {
        return Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
    }
    public static int intToSeconds(int minutes, int seconds) {
        return minutes * 60 + seconds;
    }
    public static int intToMin(int seconds) {
        return seconds/60;
    }
    public static int toRestSecond(int seconds) {
        return seconds - (seconds/60) * 60;
    }
    public static int toDuration(int workMin, int restMin, int workSec, int resSec, int rounds) {
        return (workMin + restMin) * rounds + (workSec + resSec) * rounds / 60;
    }
    public static String stringToDurationString(String workMin, String restMin, String workSec, String resSec, String rounds){
        int duration = toDuration(Integer.parseInt(workMin), Integer.parseInt(restMin), Integer.parseInt(workSec), Integer.parseInt(resSec), Integer.parseInt(rounds));
        return String.valueOf(duration);
    }
    public static String calculateTotalTimeToString(int hours, int minutes, int seconds) {
        return String.valueOf(hours * 60 + minutes + calculateSec(seconds));
    }
    public static int calculateSec(int seconds) {
        return seconds == 60? 1:0;
    }
}
