package com.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {
    private static final String TAG = ServiceActivity.class.getName();
    private LocalService mService;
    private Messenger mMessengerService;
    boolean mBound = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startServiceBtn1)
    protected void startService1(){
        startService(new Intent(ServiceActivity.this,MyService.class));
    }

    @OnClick(R.id.startServiceBtn2)
    protected void startService2(){
        if (mBound) {
            int num = mService.getRandomNumber();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.startServiceBtn3)
    protected void startService3(){
        if (!mBound){
            return;
        }
        Message message = Message.obtain();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString("reply","shou dao le.");
        message.setData(bundle);
        try {
            Log.e(TAG,"发送消息");
            mMessengerService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection2 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.e(TAG,"绑定成功");
            mMessengerService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mMessengerService = null;
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
//        Intent intent = new Intent(this, LocalService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);




        //绑定服务端的服务，此处的action是service在Manifests文件里面声明的
        Intent intent2 = new Intent();
        intent2.setAction("com.dj.messenger");
        //不要忘记了包名，不写会报错
        intent2.setPackage("com.dj.collection");
        bindService(intent2, mConnection2, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            unbindService(mConnection2);
            mBound = false;
        }
    }
}
