package com.eventbus.event;

/**
 * Created by wangjing4 on 2017/7/4.
 */

public class ToastEvent {
    public static String message;
    public ToastEvent(String message){
        this.message = message;
    }
}
