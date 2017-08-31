package com.eventbus.event;

/**
 * Created by wangjing4 on 2017/7/17.
 */

public class DataChangedEvent {
    private String message;
    public DataChangedEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
