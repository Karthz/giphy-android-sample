package com.karthz.giphy.di;

import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.presenter.Contract;

public class ComponentHelper {

    public static AppComponent create(GiphyApplication app) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }

    public static TrendingComponent createTrendingComponent(AppComponent appComponent, Contract.View view) {
        return appComponent.plus(new TrendingModule(view));
    }

    public static SearchComponent createSearchComponent(AppComponent appComponent, Contract.View view) {
        return appComponent.plus(new SearchModule(view));
    }

}
