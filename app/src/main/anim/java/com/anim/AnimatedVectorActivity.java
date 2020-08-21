package com.anim;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import android.view.View;
import android.widget.ImageView;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjing4 on 2017/7/5.
 */

public class AnimatedVectorActivity extends BaseActivity {
    @BindView(R.id.iv_2)
    AppCompatImageView appCompatImageView;
    @BindView(R.id.anImg)
    ImageView anImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_vector);
        ButterKnife.bind(this);


        AnimationDrawable anim = (AnimationDrawable) anImg.getDrawable();
        anim.start();
    }

    private boolean isTwitterChecked = false;
    @OnClick(R.id.iv_2)
    public void onTwitterClick(View view) {
        isTwitterChecked = !isTwitterChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
        appCompatImageView.setImageState(stateSet, true);
    }
}
