package com.dj.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.dj.logutil.LogUtils;

public class DispatchEventView1 extends ConstraintLayout {
    public DispatchEventView1(Context context) {
        super(context);
    }

    public DispatchEventView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchEventView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("DispatchEventView1.onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
