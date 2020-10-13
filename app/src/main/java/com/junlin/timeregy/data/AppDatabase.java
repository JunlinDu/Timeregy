package com.junlin.timeregy.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.entity.User;

@Database(entities = {User.class, TimerTemplate.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    private AppDatabase() { }

    public AppDatabase getAppDatabase(Context context) {
        if (appDatabase != null) return appDatabase;
        synchronized (this) {
            AppDatabase database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "timeregy_database"
            ).build();
            appDatabase = database;
            return database;
        }
    }

}
