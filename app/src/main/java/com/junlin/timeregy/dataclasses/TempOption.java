package com.junlin.timeregy.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TempOption implements Parcelable {
    private int titleRes;
    private int descRes;
    private int imgRes;
    private Boolean Interval;

    public TempOption(int titleRes, int descRes, int imgRes, Boolean interval) {
        this.titleRes = titleRes;
        this.descRes = descRes;
        this.imgRes = imgRes;
        Interval = interval;
    }

    protected TempOption(Parcel in) {
        titleRes = in.readInt();
        descRes = in.readInt();
        imgRes = in.readInt();
        byte tmpInterval = in.readByte();
        Interval = tmpInterval == 0 ? null : tmpInterval == 1;
    }

    public static final Creator<TempOption> CREATOR = new Creator<TempOption>() {
        @Override
        public TempOption createFromParcel(Parcel in) {
            return new TempOption(in);
        }

        @Override
        public TempOption[] newArray(int size) {
            return new TempOption[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleRes);
        dest.writeInt(descRes);
        dest.writeInt(imgRes);
        dest.writeByte((byte) (Interval == null ? 0 : Interval ? 1 : 2));
    }
}
