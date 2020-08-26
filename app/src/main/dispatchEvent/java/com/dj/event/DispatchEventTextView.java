package com.dj.event;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.dj.logutil.LogUtils;

public class DispatchEventTextView extends androidx.appcompat.widget.AppCompatTextView {

    public DispatchEventTextView(Context context) {
        super(context);
    }

    public DispatchEventTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchEventTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtils.e("onTouch");
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtils.e("ACTION_DOWN");
            return super.onTouchEvent(event);
        }else{
            LogUtils.e("ACTION_other");
            //返回为false表示view处理完onTouchEvent后不消费这次事件
            return false;
        }
    }

    //向下分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        getParent().requestDisallowInterceptTouchEvent(true);
        //return true：如果已经是最后一层view了，再向下分发后，下一级已经是null，所以下一级没有返回，则事件终止在了当前这个view
        //return false：如果已经是最后一层view了，不往下分发，则父一级viewgroup会执行事件onTouchEvent
        return super.dispatchTouchEvent(event);
    }
}
