package com.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.dj.collection.MainActivity;
import com.dj.collection.R;
import com.eventbus.event.DataChangedEvent;
import com.eventbus.event.MessageEvent;
import com.eventbus.event.ToastEvent;
import com.eventbus.manager.DataChangedManager;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_main);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.sendEventBtn)
    protected void sendMessageEvent(){
        EventBus.getDefault().post(new MessageEvent("hahahahaha"));

        DataChangedManager.getInstance().setData("1111");
    }

    @OnClick(R.id.sendToastEventBtn)
    protected void sendToastEvent(){
        EventBus.getDefault().post(new ToastEvent("好吧"));
    }

    @Subscribe
    public void onDataChangedEvent(DataChangedEvent event){
        Toast.makeText(EventBusMainActivity.this,event.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
