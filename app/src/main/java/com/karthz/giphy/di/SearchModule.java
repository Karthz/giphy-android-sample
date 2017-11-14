package com.karthz.giphy.di;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.presenter.Contract;
import com.karthz.giphy.presenter.SearchPresenter;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
class SearchModule {

    private Contract.View view;

    public SearchModule(Contract.View view) {
        this.view = view;
    }

    @Provides
    Contract.View providesView() {
        return view;
    }

    @Provides
    Contract.Presenter providesPresenter(Contract.View view, DataSource dataSource, EventBus bus) {
        return new SearchPresenter(view, dataSource, bus);
    }

}
