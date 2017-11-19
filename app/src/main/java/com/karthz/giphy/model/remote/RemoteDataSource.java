package com.karthz.giphy.model.remote;

import android.support.annotation.NonNull;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.data.ApiResponse;
import com.karthz.giphy.model.data.Gif;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.Observable;

public class RemoteDataSource implements DataSource {

    private GiphyApi giphyApi;

    public RemoteDataSource(GiphyApi giphyApi) {
        this.giphyApi = giphyApi;
    }

    @Override
    public Observable<List<Gif>> getTrendingGifs(int offset) {
        return giphyApi.getTrending(offset)
                .map(ApiResponse::getGifs);
    }

    @Override
    public Observable<List<Gif>> getSearchResults(@NonNull String searchQuery, int offset) {
        try {
            searchQuery = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            searchQuery = URLEncoder.encode(searchQuery);
        }

        return giphyApi.getSearchResults(searchQuery, offset)
                .map(ApiResponse::getGifs);
    }

//    private Callback<ApiResponse> trendingCallback = new Callback<ApiResponse>() {
//        @Override
//        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//            if (response.isSuccessful()) {
//                List<Gif> gifs = response.body().getGifs();
//                bus.post(new TrendingGifsSuccess(gifs));
//            } else {
//                bus.post(new TrendingGifsFailed());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<ApiResponse> call, Throwable t) {
//            bus.post(new TrendingGifsFailed());
//        }
//    };
//
//    private Callback<ApiResponse> searchCallback = new Callback<ApiResponse>() {
//        @Override
//        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//            if (response.isSuccessful()) {
//                List<Gif> gifs = response.body().getGifs();
//                bus.post(new SearchGifsSuccess(gifs));
//            } else {
//                bus.post(new SearchGifsFailed());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<ApiResponse> call, Throwable t) {
//            bus.post(new SearchGifsFailed());
//        }
//    };
}
