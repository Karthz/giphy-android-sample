package com.karthz.giphy.di;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.remote.GiphyApi;
import com.karthz.giphy.model.remote.RemoteDataSource;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
class DataModule {

    @Provides
    DataSource providesRemoteDataSource(GiphyApi giphyApi, EventBus bus) {
        return new RemoteDataSource(giphyApi, bus);
    }

}
