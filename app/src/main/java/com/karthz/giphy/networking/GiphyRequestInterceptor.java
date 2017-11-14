package com.karthz.giphy.networking;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GiphyRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl httpUrl = original.url();

        HttpUrl url = httpUrl.newBuilder()
                .addQueryParameter("api_key", "Yix1MlrtOBr1nrloGHR7o602HZCtE1Db")
                .build();

        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
