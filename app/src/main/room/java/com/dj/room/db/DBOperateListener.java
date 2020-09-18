package com.dj.room.db;

public interface DBOperateListener<T> {
    void onComplete(T t);
}
