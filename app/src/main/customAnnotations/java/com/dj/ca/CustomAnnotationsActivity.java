package com.dj.ca;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.dj.ca.ann.SingleClick;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.databinding.ActivityCustomAnnotationsBinding;
import com.dj.logutil.LogUtils;

/**
 * 自定义注解+Aspectj示例
 */
public class CustomAnnotationsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomAnnotationsBinding dataBinding =  DataBindingUtil.setContentView(this, R.layout.activity_custom_annotations);
        dataBinding.button.setOnClickListener(new View.OnClickListener() {

            @Override
            @SingleClick
            public void onClick(View v) {
                LogUtils.e("点击了按钮："+System.currentTimeMillis());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
