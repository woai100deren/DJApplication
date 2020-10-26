package com.dj.collection;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


/**
 * Activity生命周期监控
 */

public class ActivityLifeCycleCallbacks implements Application.ActivityLifecycleCallbacks, IActivityState {

    private List<Activity> activityList = new ArrayList<>();
    private List<Activity> resumeActivity = new ArrayList<>();
    private int mFinalCount;

    /**
     * 最后一次可见的Activity
     * 用于比对Activity，这样可以排除启动应用时的这种特殊情况，
     * 如果启动应用时也需要锁屏等操作，请在启动页里进行操作。
     */
    private Activity lastVisibleActivity;

    public void finishAllActivity() {
        try {
            if (count() > 0) {
                for (Activity activity : activityList) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() {
        return activityList.size();
    }

    @Override
    public boolean isFront() {
        return resumeActivity.size() > 0;
    }

    @Override
    public Activity current() {
        return resumeActivity.size() > 0 ? resumeActivity.get(resumeActivity.size() - 1) : null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityList.add(0, activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mFinalCount++;
        //如果mFinalCount ==1，说明是从后台到前台
        if (mFinalCount == 1){
            //说明从后台回到了前台
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!resumeActivity.contains(activity)) {
            resumeActivity.add(activity);
        }
        if (lastVisibleActivity == activity && mFinalCount == 1) {
            //Background -> Foreground , do something
            startLockScreen(activity);
        }
        lastVisibleActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        resumeActivity.remove(activity);
        mFinalCount--;
        //如果mFinalCount ==0，说明是前台到后台
        if (mFinalCount == 0){

        }
    }

    public Activity getLastActivity(){
        return activityList.size() > 0 ? activityList.get(0) : null;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 打开手势密码解锁
     * @param activity Activity
     */
    private void startLockScreen(Activity activity) {
    }

    /**
     * 判断是否要开启锁屏
     * @param activity Activity
     * @return true 锁屏，反之不锁屏
     */
    private boolean lockScreen(Activity activity) {
        return false;

    }
}
