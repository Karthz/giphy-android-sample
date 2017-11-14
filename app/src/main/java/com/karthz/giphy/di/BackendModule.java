package com.karthz.giphy.di;

import com.karthz.giphy.model.remote.GiphyApi;
import com.karthz.giphy.networking.GiphyRequestInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class BackendModule {

    @Provides
    GiphyApi providesGiphyApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GiphyApi.class);
    }

    @Provides
    OkHttpClient providesHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new GiphyRequestInterceptor());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

}
