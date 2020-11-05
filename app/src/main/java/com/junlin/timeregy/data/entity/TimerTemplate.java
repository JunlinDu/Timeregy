package com.junlin.timeregy.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.junlin.timeregy.data.enums.*;

import java.util.Date;

@Entity(tableName = "timer")
public class TimerTemplate implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    public String name;

    public boolean interval;

    @ColumnInfo(name = "work_time")
    public int workTimeInSec;

    @ColumnInfo(name = "rest_time")
    public int restTimeInSec;

    public int rounds;

    public int tag;

    public int interruptions;

    public String remark;

    @ColumnInfo(name = "date_created")
    public Date dateCreated;

    @Ignore
    private boolean showMenu = false;

    public TimerTemplate(int userId, String name, boolean interval, int workTimeInSec,
                         int restTimeInSec, int rounds, int tag, int interruptions,
                         String remark, Date dateCreated) {
        this.userId = userId;
        this.name = name;
        this.interval = interval;
        this.workTimeInSec = workTimeInSec;
        this.restTimeInSec = restTimeInSec;
        this.rounds = rounds;
        this.tag = tag;
        this.interruptions = interruptions;
        this.remark = remark;
        this.dateCreated = dateCreated;
    }

    protected TimerTemplate(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        name = in.readString();
        interval = in.readByte() != 0;
        workTimeInSec = in.readInt();
        restTimeInSec = in.readInt();
        rounds = in.readInt();
        tag = in.readInt();
        interruptions = in.readInt();
        remark = in.readString();
        showMenu = in.readByte() != 0;
    }

    public static final Creator<TimerTemplate> CREATOR = new Creator<TimerTemplate>() {
        @Override
        public TimerTemplate createFromParcel(Parcel in) {
            return new TimerTemplate(in);
        }

        @Override
        public TimerTemplate[] newArray(int size) {
            return new TimerTemplate[size];
        }
    };

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeByte((byte) (interval ? 1 : 0));
        dest.writeInt(workTimeInSec);
        dest.writeInt(restTimeInSec);
        dest.writeInt(rounds);
        dest.writeInt(tag);
        dest.writeInt(interruptions);
        dest.writeString(remark);
        dest.writeByte((byte) (showMenu ? 1 : 0));
    }
}
