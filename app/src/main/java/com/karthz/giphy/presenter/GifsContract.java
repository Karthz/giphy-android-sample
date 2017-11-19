package com.karthz.giphy.presenter;

import com.karthz.giphy.model.data.Gif;

import java.util.List;

public interface GifsContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showGifs(List<Gif> gifs);

        void showTrendingGifsFailure();

        void showSearchResultsFailure();

        void clearList();

    }

    interface Presenter {

        void subscribe();

        void unsubscribe();

        void getTrendingGifs(int offset);

        void getSearchResults(String query, int offset);

    }
}
