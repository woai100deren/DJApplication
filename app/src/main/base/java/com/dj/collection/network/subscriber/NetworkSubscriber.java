package com.dj.collection.network.subscriber;

import android.content.Context;
import com.dj.collection.network.listener.ResponseListener;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class NetworkSubscriber<T> extends Subscriber<T>{
    private ResponseListener mResponseListener;

    public NetworkSubscriber(ResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if(mResponseListener!=null){
            mResponseListener.onStart();
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if(mResponseListener!=null){
            mResponseListener.onFinish();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(mResponseListener!=null){
            mResponseListener.onFailed(e);
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if(mResponseListener!=null){
            mResponseListener.onSuccess(t);
        }
    }
}