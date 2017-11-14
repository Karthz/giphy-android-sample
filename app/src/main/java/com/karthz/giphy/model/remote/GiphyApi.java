package com.karthz.giphy.model.remote;

import com.karthz.giphy.model.data.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {

    int FETCH_SIZE = 25;

    @GET("/v1/gifs/trending?rating=G&limit=" + FETCH_SIZE)
    Call<ApiResponse> getTrending(@Query("offset") int offset);

    @GET("v1/gifs/search?rating=G")
    Call<ApiResponse> getSearchResults(@Query("q") String searchQuery, @Query("offset") int offset);

}
