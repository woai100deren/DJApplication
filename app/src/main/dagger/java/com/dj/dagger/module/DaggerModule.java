package com.dj.dagger.module;

import com.dj.dagger.iview.IDaggerView;
import com.dj.dagger.scope.DaggerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {
    private IDaggerView iDaggerView;
    public DaggerModule(IDaggerView iDaggerView){
        this.iDaggerView = iDaggerView;
    }


    @Provides
    @DaggerActivityScope
    public IDaggerView provideIdaggerView(){
        return this.iDaggerView;
    }
}
