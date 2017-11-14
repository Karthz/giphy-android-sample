package com.karthz.giphy.presenter;

import com.karthz.giphy.event.TrendingGifsFailed;
import com.karthz.giphy.event.TrendingGifsSuccess;
import com.karthz.giphy.model.DataSource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TrendingPresenter implements Contract.Presenter {

    private Contract.View trendingView;
    private DataSource trendingDataSource;
    private EventBus bus;

    public TrendingPresenter(Contract.View trendingView, DataSource trendingDataSource, EventBus bus) {
        this.trendingView = trendingView;
        this.trendingView.setPresenter(this);
        this.trendingDataSource = trendingDataSource;
        this.bus = bus;
    }

    @Override
    public void subscribe() {
        bus.register(this);
        loadGifs(0);
        trendingView.setShowLoading();
    }

    @Override
    public void unsubscribe() {
        bus.unregister(this);
    }

    @Override
    public void loadGifs(int offset) {
        trendingDataSource.getTrendingGifs(offset);
    }

    @Override
    public void searchGifs(String query, int offset) {

    }

    @Subscribe
    public void onTrendingGifsSuccessful(TrendingGifsSuccess event) {
        trendingView.showGifs(event.getGifs());
    }

    @Subscribe
    public void onTrendingGifsFailed(TrendingGifsFailed event) {
        trendingView.showFailure();
    }

}
