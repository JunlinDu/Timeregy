package com.junlin.timeregy.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.junlin.timeregy.data.entity.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUsers(User user);

    @Query("SELECT * FROM user WHERE user_name = :userName")
    public LiveData<User> retriveUsers(String userName);

}
