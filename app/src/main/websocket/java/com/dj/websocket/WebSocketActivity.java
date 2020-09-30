package com.dj.websocket;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.databinding.ActivityWebsocketBinding;
import com.dj.logutil.LogUtils;
import com.eventbus.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebSocketActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebsocketBinding dataBinding =  DataBindingUtil.setContentView(this, R.layout.activity_websocket);
        dataBinding.connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不封装的操作方式
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder().url("ws://192.168.111.102:8091/websocket/axiba").build();
//                DJWebSocketListener listener = new DJWebSocketListener();
//                WebSocket ws = okHttpClient.newWebSocket(request, listener);
////                okHttpClient.dispatcher().executorService().shutdown();

                //采用第三方sdk封装的方式
                WebSocketNetworkHelper.getInstance().connect();
            }
        });

        dataBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketNetworkHelper.getInstance().sendMessage("我是一条小鱼儿");
            }
        });

        dataBinding.asyncSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketNetworkHelper.getInstance().asyncSendMessage("我是一条异步发送的消息");
            }
        });

        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(WebSocketEvent event){
        LogUtils.e("消息："+event.message+",线程："+Thread.currentThread());
    }

    @Override
    protected void onDestroy() {
        WebSocketNetworkHelper.getInstance().closeConnect();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
