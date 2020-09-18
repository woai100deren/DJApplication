package com.dj.room.db;

import androidx.room.Room;

import com.dj.collection.DJApplication;

public class DBManager {
    private volatile static DBManager dbManager;
    private AppDataBase db;
    private DBManager(){
        db = Room.databaseBuilder(DJApplication.applicationContext(),
                AppDataBase.class, "room_db")
                .allowMainThreadQueries()//允许在主线程中查询
                .build();
    }
    public static DBManager getInstance(){
        if(dbManager == null){
            synchronized (DBManager.class){
                if(dbManager == null){
                    dbManager = new DBManager();
                }
            }
        }
        return dbManager;
    }

    public AppDataBase getDB(){
        return db;
    }
}
