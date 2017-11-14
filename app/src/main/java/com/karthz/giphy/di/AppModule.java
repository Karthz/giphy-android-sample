package com.karthz.giphy.di;

import android.content.Context;

import com.karthz.giphy.GiphyApplication;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private GiphyApplication application;

    AppModule(GiphyApplication application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

}
