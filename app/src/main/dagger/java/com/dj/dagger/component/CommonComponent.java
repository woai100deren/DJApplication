package com.dj.dagger.component;

import com.dj.dagger.module.DaggerModule;
import com.dj.dagger.scope.DaggerActivityScope;

import dagger.Component;
import dagger.android.DaggerActivity;

@DaggerActivityScope
@Component(modules = DaggerModule.class)
public interface CommonComponent {
    void inject(DaggerActivity activity);
}
