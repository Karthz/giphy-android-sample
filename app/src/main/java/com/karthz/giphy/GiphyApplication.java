package com.karthz.giphy;

import android.app.Application;
import android.content.Context;

import com.karthz.giphy.di.AppComponent;
import com.karthz.giphy.di.ComponentHelper;

public class GiphyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = ComponentHelper.create(this);
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static GiphyApplication get(Context context) {
        return (GiphyApplication) context.getApplicationContext();
    }
}
