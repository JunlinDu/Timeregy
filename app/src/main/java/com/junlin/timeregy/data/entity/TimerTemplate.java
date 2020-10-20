package com.junlin.timeregy.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.junlin.timeregy.data.enums.*;

import java.util.Date;

@Entity(tableName = "timer")
public class TimerTemplate {

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

    public Tags tag;

    public Interruptions interruptions;

    public String remark;

    public String description;

    @ColumnInfo(name = "date_created")
    public Date dateCreated;

}
