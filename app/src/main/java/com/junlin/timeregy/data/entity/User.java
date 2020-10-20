package com.junlin.timeregy.data.entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @Ignore
    public User(String userName) {
        this.userName = userName;
    }

    public User(int uid, String userName) {
        this.uid = uid;
        this.userName = userName;
    }
}
