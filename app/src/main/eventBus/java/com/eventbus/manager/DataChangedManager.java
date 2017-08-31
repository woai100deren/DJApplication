package com.eventbus.manager;

import com.eventbus.event.DataChangedEvent;
import com.eventbus.event.ToastEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wangjing4 on 2017/7/17.
 */

public class DataChangedManager {
    private static final DataChangedManager ourInstance = new DataChangedManager();

    public static DataChangedManager getInstance() {
        return ourInstance;
    }

    private DataChangedManager() {
    }

    public void setData(String value){
        Logger.d(value);
        EventBus.getDefault().post(new DataChangedEvent("好吧"));
    }
}
