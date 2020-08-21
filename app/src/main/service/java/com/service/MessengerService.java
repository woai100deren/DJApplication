package com.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import androidx.annotation.Nullable;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getName();
    static final int MSG_SAY_HELLO = 1;

    class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "接收到消息");
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    //当收到客户端的message时，显示hello
                    Log.e(TAG, msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        //返回给客户端一个IBinder实例
        return mMessenger.getBinder();
    }
}
