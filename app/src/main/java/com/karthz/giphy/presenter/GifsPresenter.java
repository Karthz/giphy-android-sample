package com.karthz.giphy.presenter;

import android.support.annotation.NonNull;

import com.karthz.giphy.model.DataSource;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.util.Scheduler;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GifsPresenter implements GifsContract.Presenter {

    private GifsContract.View gifsView;
    private DataSource dataSource;
    protected Scheduler scheduler;
    private CompositeDisposable compositeDisposable;

    public GifsPresenter(@NonNull GifsContract.View gifsView,
                         @NonNull DataSource dataSource,
                         @NonNull Scheduler scheduler) {
        this.gifsView = gifsView;
        this.dataSource = dataSource;
        this.scheduler = scheduler;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        gifsView.showLoading();
    }

    @Override
    public void unsubscribe() {
        gifsView.clearList();
        compositeDisposable.clear();
    }

    @Override
    public void getTrendingGifs(int offset) {
        if (offset == 0) {
            gifsView.clearList();
        }

        compositeDisposable.clear();
        Disposable disposable = dataSource.getTrendingGifs(offset)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        gifs -> onGifsUpdated(gifs),
                        throwable -> onTrendingFailed());
        compositeDisposable.add(disposable);
    }

    @Override
    public void getSearchResults(String query, int offset) {
        if (offset == 0) {
            gifsView.clearList();
        }

        compositeDisposable.clear();
        Disposable disposable = dataSource.getSearchResults(query, offset)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        gifs -> onGifsUpdated(gifs),
                        throwable -> onSearchFailed());
        compositeDisposable.add(disposable);
    }

    private void onGifsUpdated(List<Gif> gifs) {
        gifsView.showGifs(gifs);
        gifsView.hideLoading();
    }

    private void onTrendingFailed() {
        gifsView.hideLoading();
        gifsView.showTrendingGifsFailure();
    }

    private void onSearchFailed() {
        gifsView.hideLoading();
        gifsView.showSearchResultsFailure();
    }

}
