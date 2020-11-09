package com.dj.dagger.component;

import com.dj.dagger.MyDaggerActivity;
import com.dj.dagger.module.MyDaggerModule;

import dagger.Component;

@Component(modules = MyDaggerModule.class)
public interface MyDaggerComponent {
    void inject(MyDaggerActivity daggerActivity);
}
