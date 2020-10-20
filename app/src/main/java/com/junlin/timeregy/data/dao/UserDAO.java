package com.junlin.timeregy.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.junlin.timeregy.data.entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUsers(User user);

    @Query("SELECT * FROM user ORDER BY uid")
    List<User> retrieveAllUsers();

    @Query("SELECT * FROM user WHERE user_name = :userName")
    LiveData<User> retriveUsers(String userName);


}
