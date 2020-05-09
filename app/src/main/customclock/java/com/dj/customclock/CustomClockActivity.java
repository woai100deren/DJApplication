package com.dj.customclock;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

public class CustomClockActivity extends BaseActivity {
    private MyClockView time_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_clock);

        time_view = (MyClockView) findViewById(R.id.time_view);
        time_view.setTime(17,16, 30);
        time_view.start();
    }
}
