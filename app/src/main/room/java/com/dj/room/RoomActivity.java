package com.dj.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;
import com.dj.room.db.DBManager;
import com.dj.room.db.DBOperateListener;
import com.dj.room.db.table.User;

public class RoomActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername("wangjing");
                user.setPassword("123456");
                DBManager.getInstance().getDB().userDao().insertAll(user);
            }
        });

        findViewById(R.id.readBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = DBManager.getInstance().getDB().userDao().findByName("wangjing");
                if(user == null){
                    Toast.makeText(RoomActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RoomActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.asynReadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.getInstance().findUserByName("wangjing", new DBOperateListener<User>() {
                    @Override
                    public void onStart() {
                        LogUtils.e("开始",Thread.currentThread());
                    }

                    @Override
                    public void onComplete(User user) {
                        LogUtils.e(user.toString(),Thread.currentThread());
                        if(user == null){
                            Toast.makeText(RoomActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RoomActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.e("错误"+Thread.currentThread());
                        Toast.makeText(RoomActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        LogUtils.e("完成"+Thread.currentThread());
//                        Toast.makeText(RoomActivity.this, "最终完成", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
