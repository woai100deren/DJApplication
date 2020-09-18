package com.dj.room.db;

import android.annotation.SuppressLint;

import androidx.room.Room;

import com.dj.collection.DJApplication;
import com.dj.room.db.table.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    @SuppressLint("CheckResult")
    public void findUserByName(String name, DBOperateListener listener){
        db.userDao().asynFindByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        listener.onComplete(user);
                    }
                });
    }
}
