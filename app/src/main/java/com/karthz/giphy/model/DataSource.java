package com.karthz.giphy.model;

import android.support.annotation.NonNull;

import com.karthz.giphy.model.data.Gif;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {

    Observable<List<Gif>> getTrendingGifs(int offset);

    Observable<List<Gif>> getSearchResults(@NonNull String searchQuery, int offset);

}
