package com.dj.collection;

import android.app.Application;

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
    }
}
