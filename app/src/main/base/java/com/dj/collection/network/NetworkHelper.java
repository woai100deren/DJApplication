package com.dj.collection.network;

import com.dj.collection.network.factory.FastJsonConverterFactory;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.collection.network.subscriber.NetworkSubscriber;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求封装类
 * Created by wangjing4 on 2017/8/23.
 */

public class NetworkHelper {
    private static final String TAG = NetworkHelper.class.getName();
    private static final int DEFAULT_TIMEOUT = 10;//网络超时时间（单位秒）
//    public static final String BASE_URL = "http://www.smallsix.com.cn/upload/";//网络请求URL地址
    public static final String BASE_URL = "https://mobilelive.dz11.com/";//网络请求URL地址
    private Retrofit retrofit;
    private NetworkService mNetworkService;

    //类加载时就初始化
    private static final NetworkHelper instance = new NetworkHelper();
    public static NetworkHelper getInstance(){//饿汉式单例
        return instance;
    }
    private NetworkHelper(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mNetworkService = retrofit.create(NetworkService.class);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * so下载信息
     */
    public void getSoDownloadInfo(String oid,ResponseListener responseListener){
        Observable observable = mNetworkService.getSoDownloadInfo(oid);
        toSubscribe(observable, new NetworkSubscriber(responseListener));
    }

}
