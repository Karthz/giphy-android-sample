package com.karthz.giphy.model.remote;

import android.support.annotation.NonNull;

import com.karthz.giphy.event.SearchGifsFailed;
import com.karthz.giphy.event.SearchGifsSuccess;
import com.karthz.giphy.event.TrendingGifsFailed;
import com.karthz.giphy.event.TrendingGifsSuccess;
import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.data.ApiResponse;
import com.karthz.giphy.model.data.Gif;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {

    private GiphyApi giphyApi;
    private EventBus bus;

    public RemoteDataSource(GiphyApi giphyApi, EventBus bus) {
        this.giphyApi = giphyApi;
        this.bus = bus;
    }

    @Override
    public void getTrendingGifs(int offset) {
        Call<ApiResponse> call = giphyApi.getTrending(offset);
        call.enqueue(trendingCallback);
    }

    @Override
    public void getSearchResults(@NonNull String searchQuery, int offset) {
        try {
            searchQuery = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            searchQuery = URLEncoder.encode(searchQuery);
        }

        Call<ApiResponse> call = giphyApi.getSearchResults(searchQuery, offset);
        call.enqueue(searchCallback);
    }

    private Callback<ApiResponse> trendingCallback = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful()) {
                List<Gif> gifs = response.body().getGifs();
                bus.post(new TrendingGifsSuccess(gifs));
            } else {
                bus.post(new TrendingGifsFailed());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            bus.post(new TrendingGifsFailed());
        }
    };

    private Callback<ApiResponse> searchCallback = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful()) {
                List<Gif> gifs = response.body().getGifs();
                bus.post(new SearchGifsSuccess(gifs));
            } else {
                bus.post(new SearchGifsFailed());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            bus.post(new SearchGifsFailed());
        }
    };
}
