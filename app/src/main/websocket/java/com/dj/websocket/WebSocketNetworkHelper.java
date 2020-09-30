package com.dj.websocket;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.dj.collection.network.HttpsUtils;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.collection.network.subscriber.NetworkSubscriber;
import com.dj.logutil.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;
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

public class WebSocketNetworkHelper {
    private static final String TAG = WebSocketNetworkHelper.class.getName();
    private static final int DEFAULT_TIMEOUT = 10;//网络超时时间（单位秒）
    public static final String BASE_URL = "ws://192.168.111.101:8091//websocket/axiba";//网络请求URL地址
    private volatile static WebSocketNetworkHelper webSocketNetworkHelper;
    private WebSocket mWebSocket;
    private WebSocketSubscriber webSocketSubscriber;
    private WebSocketNetworkHelper(){
        initWebSocket();
    }

    /**
     * 单例模式，获取网络工具类
     * @return WebSocketNetworkHelper
     */
    public static WebSocketNetworkHelper getInstance(){
        if(webSocketNetworkHelper == null){
            synchronized (WebSocketNetworkHelper.class){
                if(webSocketNetworkHelper == null){
                    webSocketNetworkHelper = new WebSocketNetworkHelper();
                }
            }
        }
        return webSocketNetworkHelper;
    }

    /**
     * 网络连接基本配置
     */
    private void initWebSocket() {
        Config config = new Config.Builder()
                .setShowLog(true, TAG)
                .setClient(new OkHttpClient.Builder().pingInterval(DEFAULT_TIMEOUT,TimeUnit.SECONDS).build())
                .setReconnectInterval(5, TimeUnit.SECONDS)  //set reconnect interval
                .build();
        RxWebSocket.setConfig(config);
    }

    public void connect(){
        if(webSocketSubscriber!=null){
            LogUtils.e(TAG, "webSocket已经发起了连接，不能再连接。");
            return;
        }
        webSocketSubscriber = new WebSocketSubscriber() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket) {
                LogUtils.e(TAG, "webSocket连接成功。");
                mWebSocket = webSocket;
            }

            @Override
            public void onMessage(@NonNull String text) {
                Log.e(TAG, "收到服务器返回数据String:" + text);

                //如果此处返回在主线程，如果需要处理的数据较大，个人认为再开线程池进行数据转换，然后在转换完之后，EventBus将数据传递出去。
                EventBus.getDefault().post(new WebSocketEvent(text));
            }

            @Override
            public void onMessage(@NonNull ByteString byteString) {
                LogUtils.e(TAG, "收到服务器返回数据ByteString:" + byteString.toString());
//                ResponseManager.handleResponse(byteString,WebSocketActivity.this);

                //如果此处返回在主线程，如果需要处理的数据较大，个人认为再开线程池进行数据转换，然后在转换完之后，EventBus将数据传递出去。
                EventBus.getDefault().post(new WebSocketEvent(byteString.toString()));
            }

            @Override
            protected void onReconnect() {
                LogUtils.e(TAG, "重连:");
            }

            @Override
            protected void onClose() {
                LogUtils.e(TAG, "webSocket断开");
                mWebSocket = null;
                webSocketSubscriber = null;
            }
        };
        RxWebSocket.get(BASE_URL).subscribe(webSocketSubscriber);
    }

    public void closeConnect(){
        if(webSocketSubscriber!=null){
            webSocketSubscriber.dispose();
        }
    }

    public void sendMessage(String message){
        if(mWebSocket!=null){
            mWebSocket.send(message);
        }
    }

    /**
     * 异步发送,若WebSocket已经打开,直接发送,若没有打开,打开一个WebSocket发送完数据,直接关闭.
     */
    public void asyncSendMessage(String message){
        RxWebSocket.asyncSend(BASE_URL,message);
    }
}
