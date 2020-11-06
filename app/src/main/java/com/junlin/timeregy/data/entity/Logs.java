package com.junlin.timeregy.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "logs")
public class Logs {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user_id")
    public int userId;
    public String name;
    public Date date;

    public Logs(int userId, String name, Date date) {
        this.userId = userId;
        this.name = name;
        this.date = date;
    }
}
