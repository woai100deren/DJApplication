package com.dj.dagger;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.dagger.bean.Cloth;
import com.dj.dagger.component.DaggerMyDaggerComponent;
import com.dj.dagger.component.MyDaggerComponent;
import com.dj.dagger.module.MyDaggerModule;

import javax.inject.Inject;

public class MyDaggerActivity extends BaseActivity {
    @Inject
    Cloth cloth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        TextView textView = findViewById(R.id.textView);

        MyDaggerComponent myDaggerComponent = DaggerMyDaggerComponent.builder().myDaggerModule(new MyDaggerModule()).build();
        myDaggerComponent.inject(this);
        textView.setText("我现在有" + cloth);
    }
}
