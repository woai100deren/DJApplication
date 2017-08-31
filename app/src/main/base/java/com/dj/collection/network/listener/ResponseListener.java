package com.dj.collection.network.listener;

/**
 * 网络请求
 * @param <T>
 */
public interface ResponseListener<T> {
    void onStart();
    void onSuccess(T t);
    void onFailed(Throwable e);
    void onFinish();
}
