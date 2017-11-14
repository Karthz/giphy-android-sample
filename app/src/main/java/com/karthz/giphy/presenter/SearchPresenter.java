package com.karthz.giphy.presenter;

import com.karthz.giphy.event.SearchGifsFailed;
import com.karthz.giphy.event.SearchGifsSuccess;
import com.karthz.giphy.model.DataSource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SearchPresenter implements Contract.Presenter {

    private Contract.View searchView;
    private DataSource dataSource;
    private EventBus bus;

    public SearchPresenter(Contract.View searchView, DataSource dataSource, EventBus bus) {
        this.searchView = searchView;
        this.searchView.setPresenter(this);
        this.dataSource = dataSource;
        this.bus = bus;
    }

    @Override
    public void subscribe() {
        bus.register(this);
    }

    @Override
    public void unsubscribe() {
        bus.unregister(this);
    }

    @Override
    public void loadGifs(int offset) {
    }

    @Override
    public void searchGifs(String query, int offset) {
        dataSource.getSearchResults(query, offset);
    }

    @Subscribe
    public void onSearchGifsSuccessful(SearchGifsSuccess event) {
        searchView.showGifs(event.getGifs());
    }

    @Subscribe
    public void onSearchGifsFailed(SearchGifsFailed event) {
        searchView.showFailure();
    }

}
