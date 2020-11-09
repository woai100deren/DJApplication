package com.dj.dagger.module;

import com.dj.dagger.bean.Cloth;

import dagger.Module;
import dagger.Provides;

@Module
public class MyDaggerModule {
    @Provides
    public Cloth getCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }
}
