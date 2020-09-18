package com.dj.room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dj.room.db.dao.UserDao;
import com.dj.room.db.table.User;

@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
