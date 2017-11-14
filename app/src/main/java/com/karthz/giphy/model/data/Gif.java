package com.karthz.giphy.model.data;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Gif {

    @SerializedName("images")
    private Images images;

    public Images getImages() {
        return images;
    }

    public Uri getFixedSmallUri() {
        return Uri.parse(images.reduced.getUrl());
    }

    public String getOriginalUrl() {
        return images.original.getUrl();
    }

    private static class Images {

        @SerializedName("fixed_height_downsampled")
        private Image reduced;

        @SerializedName("fixed_height")
        private Image fixedHeight;

        @SerializedName("original")
        private Image original;

    }
}
