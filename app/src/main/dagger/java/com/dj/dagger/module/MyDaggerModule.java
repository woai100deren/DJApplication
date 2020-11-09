package com.dj.dagger.module;

import com.dj.dagger.bean.Cloth;
import com.dj.dagger.bean.Clothes;

import javax.inject.Named;

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

    @Provides
    public Clothes getClothes(Cloth cloth){
        return new Clothes(cloth);
    }

    @Provides
    @Named("red")
    public Cloth getRedCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }
    @Provides
    @Named("blue")
    public Cloth getBlueCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }
}
