package com.karthz.giphy.presenter;

import com.karthz.giphy.BasePresenter;
import com.karthz.giphy.BaseView;
import com.karthz.giphy.model.data.Gif;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface Contract {

    interface View extends BaseView<Presenter> {

        void setShowLoading();

        void showGifs(List<Gif> gifs);

        void showGifDetailsUi(Gif gif);

        void showFailure();

    }

    interface Presenter extends BasePresenter {

        void loadGifs(int offset);

        void searchGifs(String query, int offset);

    }
}
