package com.dj.bugly;

import com.dj.bugly.exception.MyException;

/**
 * Created by wangjing4 on 2017/7/20.
 */

public enum Response {

    SCUUESS(MyException.class);

    private Class<?> mClass;

    private Response(Class<?> mClass) {
        this.mClass = mClass;
    }

    public Class<?> getmClass() {
        return mClass;
    }

    public void setmClass(Class<?> mClass) {
        this.mClass = mClass;
    }
}
