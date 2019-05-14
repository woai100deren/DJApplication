package com.dj.dagger.presenter;

import android.content.Context;
import android.widget.Toast;

import com.dj.dagger.iview.IDaggerView;
import com.dj.dagger.model.DaggerModel;

import javax.inject.Inject;

public class DaggerPresenter {
    IDaggerView iDaggerView;
    @Inject
    public DaggerPresenter(IDaggerView iDaggerView){
        this.iDaggerView = iDaggerView;
    }

    public void test(DaggerModel daggerModel){
        Context mContext = iDaggerView.getContext();
        Toast.makeText(mContext,"dagger test......",Toast.LENGTH_SHORT).show();
    }
}
