package com.karthz.giphy.model.remote;

import com.karthz.giphy.model.data.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    int FETCH_SIZE = 25;

    @GET("/v1/gifs/trending?rating=G&limit=" + FETCH_SIZE)
    Observable<ApiResponse> getTrending(@Query("offset") int offset);

    @GET("v1/gifs/search?rating=G")
    Observable<ApiResponse> getSearchResults(@Query("q") String searchQuery, @Query("offset") int offset);

}
