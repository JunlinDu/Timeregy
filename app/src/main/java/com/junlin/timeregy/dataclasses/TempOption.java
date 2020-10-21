package com.junlin.timeregy.dataclasses;

public class TempOptions {
    private int titleRes;
    private int descRes;
    private int imgRes;
    private Boolean Interval;

    public TempOptions(int titleRes, int descRes, int imgRes, Boolean interval) {
        this.titleRes = titleRes;
        this.descRes = descRes;
        this.imgRes = imgRes;
        Interval = interval;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getDescRes() {
        return descRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public Boolean getInterval() {
        return Interval;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public void setDescRes(int descRes) {
        this.descRes = descRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setInterval(Boolean interval) {
        Interval = interval;
    }
}
