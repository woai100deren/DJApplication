package com.anim;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjing4 on 2017/7/6.
 */

public class DataChangeActivity extends BaseActivity {
    @BindView(R.id.imageView1)
    ImageView imageView1;

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_change);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startBtn)
    protected void startAnim(){
        Animation loadingAnimIn = AnimationUtils.loadAnimation(DataChangeActivity.this, R.anim.anim_datachange_in);
        loadingAnimIn.setInterpolator(new BounceInterpolator());//弹跳跃几段

        Animation loadingAnimOut = AnimationUtils.loadAnimation(DataChangeActivity.this, R.anim.anim_datachange_out);
        loadingAnimOut.setInterpolator(new LinearInterpolator());//匀速
        loadingAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView2.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView1.startAnimation(loadingAnimIn);
        imageView2.startAnimation(loadingAnimOut);
    }
}
