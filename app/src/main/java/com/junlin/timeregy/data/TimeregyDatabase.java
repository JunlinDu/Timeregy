package com.junlin.timeregy.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.junlin.timeregy.data.dao.LogsDAO;
import com.junlin.timeregy.data.dao.TimerTemplateDAO;
import com.junlin.timeregy.data.entity.Logs;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.entity.User;
import com.junlin.timeregy.data.utility.DateConverter;

@Database(entities = {User.class, TimerTemplate.class, Logs.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TimeregyDatabase extends RoomDatabase {

    // Tag for logging
    public static final String TAG = TimeregyDatabase.class.getSimpleName();

    // In the singleton pattern this need to be passed to the synchronized block,
    // Referenced from https://www.youtube.com/watch?time_continue=35&v=WJb4_IaeDnQ&feature=emb_logo
    public static final Object OBJECT = new Object();

    // Name of the database
    public static final String DATABASE_NAME = "timeregy_database";

    // database instance
    private static TimeregyDatabase database;

    // returning the instance of the database using singleton pattern
    public static TimeregyDatabase getAppDatabase(Context context) {

        if (database != null) return database;

        synchronized (OBJECT) {
            Log.i(TAG, "New Database Created!");
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TimeregyDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return database;
    }

    public abstract TimerTemplateDAO timerTemplateDAO();
    public abstract LogsDAO logsDAO();

}
