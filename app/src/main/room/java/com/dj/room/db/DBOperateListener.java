package com.dj.room.db;

public interface DBOperateListener<T> {
    void onStart();
    void onComplete(T t);
    void onError(Throwable throwable);
    void onFinish();
}
