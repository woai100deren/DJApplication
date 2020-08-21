package com.dj.bugly;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.dj.bugly.exception.MyException;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjing4 on 2017/7/20.
 */

public class BuglyActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugly);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.uploadBuglyBtn)
    protected void uploadBugly(){
        try{
            int a=1,b=0;
            System.out.println(a/b);
        }catch (Exception e){
            MyException my = new MyException("哈哈哈22222|"+e.getMessage());
            CrashReport.putUserData(BuglyActivity.this,"aa","啊");
            CrashReport.postCatchedException(new Throwable(my));
        }
    }
}
