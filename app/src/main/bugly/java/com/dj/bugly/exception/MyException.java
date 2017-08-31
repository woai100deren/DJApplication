package com.dj.bugly.exception;

/**
 * Created by wangjing4 on 2017/7/20.
 */

public class MyException extends Exception {
    public MyException() {
    }

    public MyException(String detailMessage) {
        super(detailMessage);
    }

    public MyException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MyException(Throwable throwable) {
        super(throwable);
    }
}
