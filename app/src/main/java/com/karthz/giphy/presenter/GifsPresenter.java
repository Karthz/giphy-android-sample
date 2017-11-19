package com.karthz.giphy.presenter;

import android.support.annotation.NonNull;

import com.karthz.giphy.event.SearchGifsFailed;
import com.karthz.giphy.event.SearchGifsSuccess;
import com.karthz.giphy.event.TrendingGifsFailed;
import com.karthz.giphy.event.TrendingGifsSuccess;
import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.data.Gif;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class GifsPresenter implements GifsContract.Presenter {

    private GifsContract.View gifsView;
    private DataSource dataSource;
    private EventBus bus;

    public GifsPresenter(@NonNull GifsContract.View gifsView,
                         @NonNull DataSource dataSource,
                         @NonNull EventBus bus) {
        this.gifsView = gifsView;
        this.dataSource = dataSource;
        this.bus = bus;
    }

    @Override
    public void subscribe() {
        bus.register(this);
        gifsView.showLoading();
    }

    @Override
    public void unsubscribe() {
        gifsView.clearList();
        bus.unregister(this);
    }

    @Override
    public void getTrendingGifs(int offset) {
        dataSource.getTrendingGifs(offset);
    }

    @Override
    public void getSearchResults(String query, int offset) {
        if (offset == 0) {
            gifsView.clearList();
        }

        dataSource.getSearchResults(query, offset);
    }

    private void onGifsUpdated(List<Gif> gifs) {
        gifsView.showGifs(gifs);
        gifsView.hideLoading();
    }

    @Subscribe
    public void onTrendingGifsSuccessful(TrendingGifsSuccess event) {
        onGifsUpdated(event.getGifs());
    }

    @Subscribe
    public void onTrendingGifsFailed(TrendingGifsFailed event) {
        gifsView.showTrendingGifsFailure();
    }

    @Subscribe
    public void onSearchGifsSuccessful(SearchGifsSuccess event) {
        onGifsUpdated(event.getGifs());
    }

    @Subscribe
    public void onSearchGifsFailed(SearchGifsFailed event) {
        gifsView.showSearchResultsFailure();
    }

}
