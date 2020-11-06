package com.junlin.timeregy.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.junlin.timeregy.data.entity.Logs;

@Dao
public interface LogsDAO {
    @Insert
    void insertLog(Logs log);
}
