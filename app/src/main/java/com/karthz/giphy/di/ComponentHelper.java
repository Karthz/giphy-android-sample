package com.karthz.giphy.di;

import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.presenter.GifsContract;

public class ComponentHelper {

    public static AppComponent create(GiphyApplication app) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }

    public static GifsComponent createTrendingComponent(AppComponent appComponent, GifsContract.View view) {
        return appComponent.plus(new GifsModule(view));
    }

}
