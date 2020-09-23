package com.dj.ws;

import android.os.Build;

import com.dj.collection.network.HttpsUtils;
import com.dj.collection.network.factory.FastJsonConverterFactory;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.collection.network.subscriber.NetworkSubscriber;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求封装类
 * Created by wangjing4 on 2017/8/23.
 */

public class WSNetworkHelper {
    private static final String TAG = WSNetworkHelper.class.getName();
    private static final int DEFAULT_TIMEOUT = 10;//网络超时时间（单位秒）
    public static final String BASE_URL = "http://www.webxml.com.cn/";//网络请求URL地址
    private Retrofit retrofit;
    private WSNetworkService mNetworkService;

    //类加载时就初始化
    private static final WSNetworkHelper instance = new WSNetworkHelper();
    public static WSNetworkHelper getInstance(){//饿汉式单例
        return instance;
    }
    private WSNetworkHelper(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //忽略ssl证书,android10及以上的版本就不用了
        if (Build.VERSION.SDK_INT < 29) {
            builder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        }
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())      //添加 String类型[ Scalars (primitives, boxed, and String)] 转换器
                .addConverterFactory(SimpleXmlConverterFactory.create())    //添加 xml数据类型 bean-<xml
                .addConverterFactory(GsonConverterFactory.create())         //添加 json数据类型 bean->json
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mNetworkService = retrofit.create(WSNetworkService.class);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 获取天气
     */
    public void getWeather(String body,ResponseListener responseListener){
        Observable observable = mNetworkService.getWeather(body);
        toSubscribe(observable, new NetworkSubscriber(responseListener));
    }
}
