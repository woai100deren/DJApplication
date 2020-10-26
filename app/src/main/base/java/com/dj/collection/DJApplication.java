package com.dj.collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.multidex.MultiDexApplication;

import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.LogUtil;
import com.dj.logutil.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 日志公共类
 * Created by wangjing4 on 2017/7/20.
 */

public class DJApplication extends MultiDexApplication {
    private static Context context;
    /**
     * 生命周期管理工具
     */
    private ActivityLifeCycleCallbacks activityLifeCycleCallbacks = new ActivityLifeCycleCallbacks();
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //注册生命周期监听
        initRegisterActivity();
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

    public static Context applicationContext(){
        return context;
    }

    /**
     * 非内核终止退出app调用注销
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(activityLifeCycleCallbacks);
    }

    /**
     * 注册生命周期监听
     */
    private void initRegisterActivity() {
        registerActivityLifecycleCallbacks(activityLifeCycleCallbacks);
    }

    /**
     * 获得生命周期监听管理、外部控制调用，如关闭所有的acitivty
     *
     * @return
     */
    public ActivityLifeCycleCallbacks getActivityLifeCycleCallbacks() {
        return activityLifeCycleCallbacks;
    }

    /**
     * 退出客户端
     */
    public void exitApp() {
        activityLifeCycleCallbacks.finishAllActivity();
    }
}
