package com.example.yandre.rx_room_retrofit_mvp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponseDao;

@Database(entities = {UsersResponse.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UsersResponseDao usersResponseDao();
}
