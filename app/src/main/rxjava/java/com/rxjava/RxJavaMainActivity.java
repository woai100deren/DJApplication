package com.rxjava;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.dj.collection.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaMainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startRxJava)
    protected void startRxJava(){
        Single.create(new Single.OnSubscribe<String>() {//发送数据者（被订阅者）
            @Override
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onSuccess("啊哈哈哈哈");
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleSubscriber<String>() {//接收数据者（订阅者）
            @Override
            public void onSuccess(String tags) {
                Logger.e(tags);
            }

            @Override
            public void onError(Throwable error) {
            }
        });
    }
}
