package com.junlin.timeregy.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.junlin.timeregy.data.entity.TimerTemplate;

import java.util.List;

@Dao
public interface TimerTemplateDAO {

    @Query("SELECT * FROM timer ORDER BY id")
    LiveData<List<TimerTemplate>> retrieveAllTimerTemplates();

    @Insert
    void inserTemplate(TimerTemplate timerTemplate);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTemplate(TimerTemplate timerTemplate);

    @Delete
    void deleteTemplate(TimerTemplate timerTemplate);

}
