package com.karthz.giphy.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewSwitcher;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.R;
import com.karthz.giphy.di.ComponentHelper;
import com.karthz.giphy.di.GifsComponent;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.model.remote.GiphyApi;
import com.karthz.giphy.presenter.GifsContract;
import com.karthz.giphy.ui.adapter.EndlessRecyclerViewScrollListener;
import com.karthz.giphy.ui.adapter.GifsListAdapter;
import com.karthz.giphy.ui.util.ItemDecoration;
import com.karthz.giphy.util.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GifsListFragment extends Fragment
        implements GifsContract.View, EndlessRecyclerViewScrollListener.ScrollListener,
        GifsListAdapter.Listener {

    @Inject
    GifsContract.Presenter presenter;

    @Inject
    Scheduler scheduler;

    private GifsComponent gifsComponent;

    private View rootView;
    private SearchView searchView;
    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private GifsListAdapter adapter;
    private GridLayoutManager layoutManager;
    private GifsListListener listener;
    private CompositeDisposable compositeDisposable;

    private static final long SEARCH_DELAY = 500;
    private final int INDEX_PROGRESS = 0;
    private final int INDEX_LIST = 1;

    public interface GifsListListener {
        void onItemSelected(Gif item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gifsComponent = ComponentHelper.createTrendingComponent(GiphyApplication.get(getActivity()).getAppComponent(), this);
        gifsComponent.inject(this);
        adapter = new GifsListAdapter(getActivity(), new ArrayList<Gif>(), this);
        layoutManager = new GridLayoutManager(getActivity(), 3);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
        loadGifs(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_list, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);

        rootView = view.findViewById(R.id.root_view);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnCloseListener(onCloseListener);

        viewSwitcher = view.findViewById(R.id.view_switcher);
        recyclerView = view.findViewById(R.id.gifs_list);
        setupRecyclerView();

        Observable<CharSequence> searchViewObservable = RxSearchView.queryTextChanges(searchView);
        compositeDisposable = new CompositeDisposable();

        Disposable searchDisposable = searchViewObservable
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(scheduler.ui())
                .subscribe(query -> {
                    if (!TextUtils.isEmpty(query)) {
                        presenter.getSearchResults(query.toString(), 0);
                    }
                });

        compositeDisposable.add(searchDisposable);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GifsListListener) {
            listener = (GifsListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement "
                    + GifsListListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(getActivity(), R.dimen.gif_item_offset));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(GiphyApi.FETCH_SIZE);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, this));
    }

    private SearchView.OnCloseListener onCloseListener = () -> {
        presenter.getTrendingGifs(0);
        return false;
    };

    private void loadGifs(int offset) {
        String searchQuery = searchView.getQuery().toString();
        if (TextUtils.isEmpty(searchQuery)) {
            presenter.getTrendingGifs(offset);
        } else {
            presenter.getSearchResults(searchQuery, offset);
        }
    }

    @Override
    public void onItemSelected(Gif item) {
        if (listener != null) {
            listener.onItemSelected(item);
        }
    }

    @Override
    public void showLoading() {
        viewSwitcher.setDisplayedChild(INDEX_PROGRESS);
    }

    @Override
    public void hideLoading() {
        viewSwitcher.setDisplayedChild(INDEX_LIST);
    }

    @Override
    public void showGifs(List<Gif> gifs) {
        adapter.addData(gifs);
    }

    @Override
    public void showTrendingGifsFailure() {
        Snackbar.make(rootView, R.string.trending_gifs_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, actionListener)
                .show();
    }

    @Override
    public void showSearchResultsFailure() {
        Snackbar.make(rootView, R.string.search_results_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, actionListener)
                .show();
    }

    @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void onLoadMore(int offset) {
        loadGifs(offset);
    }

    private View.OnClickListener actionListener = view -> loadGifs(0);
}
