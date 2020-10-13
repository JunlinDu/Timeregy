package com.junlin.timeregy.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.junlin.timeregy.data.enums.*;

@Entity(tableName = "timer")
public class TimerTemplate {

    @PrimaryKey(autoGenerate = true) public int id;

    public int userId;

    public String name;

    public boolean interval;

    @ColumnInfo(name = "work_time") public int workTimeInSec;

    @ColumnInfo(name = "rest_time") public int restTimeInSec;

    public int rounds;

    public Tags tag;

    public Interruption interruption;

    public String remark;

    public String description;

}
