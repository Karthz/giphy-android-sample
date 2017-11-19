package com.karthz.giphy.di;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.presenter.GifsContract;
import com.karthz.giphy.presenter.GifsPresenter;
import com.karthz.giphy.util.Scheduler;

import dagger.Module;
import dagger.Provides;

@Module
class GifsModule {

    private GifsContract.View view;

    public GifsModule(GifsContract.View view) {
        this.view = view;
    }

    @Provides
    GifsContract.View providesView() {
        return view;
    }

    @Provides
    GifsContract.Presenter providesPresenter(GifsContract.View view, DataSource dataSource, Scheduler scheduler) {
        return new GifsPresenter(view, dataSource, scheduler);
    }

}
