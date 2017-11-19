package com.karthz.giphy.di;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.presenter.GifsContract;
import com.karthz.giphy.presenter.GifsPresenter;

import org.greenrobot.eventbus.EventBus;

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
    GifsContract.Presenter providesPresenter(GifsContract.View view, DataSource dataSource, EventBus bus) {
        return new GifsPresenter(view, dataSource, bus);
    }

}
