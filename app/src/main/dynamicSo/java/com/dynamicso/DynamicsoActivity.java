package com.dynamicso;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.dj.collection.R;
import com.dj.collection.utils.Utils;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjing4 on 2017/7/6.
 */

public class DynamicsoActivity extends Activity {
    @BindView(R.id.textView)
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamicso);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.showSoBtn)
    protected void showSo(){
        System.load(Utils.getDataDir(DynamicsoActivity.this)+ File.separator+"dynamic.so");
        textView.setText(new SoUtils().getDynamicsoString());
    }
}
