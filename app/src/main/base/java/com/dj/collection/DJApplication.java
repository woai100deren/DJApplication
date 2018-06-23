package com.dj.collection;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.LogUtil;
import com.dj.logutil.LogUtils;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 日志公共类
 * Created by wangjing4 on 2017/7/20.
 */

public class DJApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "0fa389210e", false);

        if (BuildConfig.DEBUG) {
            //log配置
            LogUtils.setDebug(true);
            LogUtils.setNativeLog(true);
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new ConnectivityChangedReceiver(), filter);
        if(BuildConfig.DEBUG) {
            LogUtil.enalbeDebug();
        } else {
            LogUtil.disableDebug();
        }
        AlivcLivePusher.showDebugView(this);
    }

    class ConnectivityChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
