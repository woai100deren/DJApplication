package com.dj.zip;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.utils.Utils;
import com.dj.zip.utils.ZipUtils;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangjing4 on 2017/7/21.
 */

public class ZipActivity extends BaseActivity{
    @BindView(R.id.textView)
    TextView textView;

    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);
        ButterKnife.bind(this);

        handler = new Handler();
    }

    @OnClick(R.id.unzipBtn)
    protected void unzip(){
        try {
            ZipUtils.unzip(new File(Environment.getExternalStorageDirectory(), "mp3.zip"), Utils.getDataDir(ZipActivity.this), new CallBack() {
                @Override
                public void complete(final String progress) {
                    System.out.println("progress = "+progress);
                    textView.setText(progress);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
