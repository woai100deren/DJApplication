package com.detail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjing4 on 2017/7/31.
 */

public class DetailActivity extends BaseActivity {
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.imageView)
    ImageView imageView;


    // 记录首次按下位置
    private float startY = 0;
    private int windowsWeith;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        // 获取屏幕宽高
        DisplayMetrics metric; metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        windowsWeith = metric.widthPixels;
    }
}
