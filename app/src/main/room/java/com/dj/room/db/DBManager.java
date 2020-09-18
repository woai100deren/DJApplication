package com.dj.room.db;

import android.annotation.SuppressLint;

import androidx.room.Room;

import com.dj.collection.DJApplication;
import com.dj.logutil.LogUtils;
import com.dj.room.db.table.User;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
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
//        db.userDao().asynFindByName(name)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<User>() {//相当于onNext
//                    @Override
//                    public void accept(User user) throws Exception {
//                        listener.onComplete(user);
//                    }
//                }, new Consumer<Throwable>() {//相当于onError
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        listener.onError(throwable);
//                    }
//                });

        db.userDao().asynFindByName(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        listener.onStart();
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(User user) {
                        listener.onComplete(user);
                        onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        //onError和onComplete互斥，只会调用一个，这里手动调用一下finish
                        listener.onError(t);
                        listener.onFinish();
                    }

                    @Override
                    public void onComplete() {
                        listener.onFinish();
                    }
                });
    }
}
