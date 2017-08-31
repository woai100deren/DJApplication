package com.dj.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dj.collection.bean.SoResponseBean;
import com.dj.collection.network.NetworkHelper;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.logutil.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjing4 on 2017/8/23.
 */

public class RetrofitActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.initBtn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.initBtn:
                getMovie();
                break;
        }
    }

    private ResponseListener<SoResponseBean> responseListener = new ResponseListener<SoResponseBean>() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(SoResponseBean data) {
            LogUtils.e(data.toString());
        }

        @Override
        public void onFailed(Throwable e) {

        }

        @Override
        public void onFinish() {

        }
    };

    //进行网络请求
    private void getMovie(){
        NetworkHelper.getInstance().getSoDownloadInfo("23",responseListener);
    }
}
