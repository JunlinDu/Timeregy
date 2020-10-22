package com.junlin.timeregy.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class TempOption implements Parcelable {
    public int titleRes;
    public int descRes;
    public int imgRes;
    public Boolean Interval;
    public int workMin;
    public int workSec;
    public int restMin;
    public int resSec;
    public int rounds;
    public int duration;
    public int tag;
    public int interruptions;

    public TempOption(int titleRes, int descRes, int imgRes, Boolean interval, int workMin, int workSec, int restMin, int resSec, int rounds, int tag, int interruptions) {
        this.titleRes = titleRes;
        this.descRes = descRes;
        this.imgRes = imgRes;
        this.Interval = interval;
        this.workMin = workMin;
        this.workSec = workSec;
        this.restMin = restMin;
        this.resSec = resSec;
        this.rounds = rounds;
        this.tag = tag;
        this.interruptions = interruptions;
        this.duration = (workMin + restMin) * rounds + (workSec + resSec) * rounds / 60;
    }

    protected TempOption(Parcel in) {
        titleRes = in.readInt();
        descRes = in.readInt();
        byte tmpInterval = in.readByte();
        Interval = tmpInterval == 0 ? null : tmpInterval == 1;
        workMin = in.readInt();
        workSec = in.readInt();
        restMin = in.readInt();
        resSec = in.readInt();
        rounds = in.readInt();
        duration = in.readInt();
        tag  = in.readInt();
        interruptions = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleRes);
        dest.writeInt(descRes);
        dest.writeByte((byte) (Interval == null ? 0 : Interval ? 1 : 2));
        dest.writeInt(workMin);
        dest.writeInt(workSec);
        dest.writeInt(restMin);
        dest.writeInt(resSec);
        dest.writeInt(rounds);
        dest.writeInt(duration);
        dest.writeInt(tag);
        dest.writeInt(interruptions);
    }
}
