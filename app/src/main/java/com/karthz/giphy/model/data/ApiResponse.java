package com.karthz.giphy.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("data")
    private List<Gif> gifs;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<Gif> getGifs() {
        return gifs;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
