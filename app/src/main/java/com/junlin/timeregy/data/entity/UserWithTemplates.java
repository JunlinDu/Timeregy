package com.junlin.timeregy.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.entity.User;

import java.util.List;

public class UserWithTemplates {
    @Embedded public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "userId"
    )
    public List<TimerTemplate> timerTemplateList;
}
