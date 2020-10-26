package com.dj.collection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.eventbus.EventBusMainActivity;
import com.eventbus.event.LoginEvent;
import com.eventbus.event.MessageEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wangjing4 on 2017/7/7.
 */

public class BaseActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event){
        Toast.makeText(this,"去登录界面",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, EventBusMainActivity.class);
        startActivity(intent);
    }
}
