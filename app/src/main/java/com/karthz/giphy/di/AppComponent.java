package com.karthz.giphy.di;

import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                BackendModule.class,
                DataModule.class
        }
)
public interface AppComponent {

    GifsComponent plus(GifsModule module);

    void inject(GiphyApplication application);

    void inject(MainActivity activity);

}
