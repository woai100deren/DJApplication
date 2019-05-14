package com.dj.dagger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.dagger.iview.IDaggerView;
import com.dj.dagger.presenter.DaggerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaggerMainActvity extends BaseActivity implements IDaggerView {
    @BindView(R.id.daggerTestBtn)
    Button button;

    @Inject
    DaggerPresenter daggerPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger_main);
        ButterKnife.bind(this);

//        DaggerCommonComponent.
//                builder().
//                commonModule(new CommonModule(this)).
//                build().
//                Inject(this);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.daggerTestBtn)
    void daggerTest(){
    }
}
