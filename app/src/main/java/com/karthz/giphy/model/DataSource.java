package com.karthz.giphy.model;

import android.support.annotation.NonNull;

public interface DataSource {

    void getTrendingGifs(int offset);

    void getSearchResults(@NonNull String searchQuery, int offset);

}
