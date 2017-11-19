package com.karthz.giphy.di;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.remote.GiphyApi;
import com.karthz.giphy.model.remote.RemoteDataSource;
import com.karthz.giphy.util.Scheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DataModule {

    @Provides @Singleton
    Scheduler providesScheduler() {
        return new Scheduler();
    }

    @Provides
    DataSource providesRemoteDataSource(GiphyApi giphyApi) {
        return new RemoteDataSource(giphyApi);
    }

}
