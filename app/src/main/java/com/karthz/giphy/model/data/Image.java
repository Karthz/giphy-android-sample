package com.karthz.giphy.model.data;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("url")
    private String url;

    @SerializedName("webp")
    private String webp;

    public String getUrl() {
        return url;
    }

    public String getWebp() {
        return webp;
    }
}
