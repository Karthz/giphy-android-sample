package com.karthz.giphy.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.R;
import com.karthz.giphy.di.ComponentHelper;
import com.karthz.giphy.di.SearchComponent;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.model.remote.GiphyApi;
import com.karthz.giphy.presenter.Contract;
import com.karthz.giphy.ui.adapter.EndlessRecyclerViewScrollListener;
import com.karthz.giphy.ui.adapter.GifsListAdapter;
import com.karthz.giphy.ui.util.ItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends Fragment
        implements Contract.View, EndlessRecyclerViewScrollListener.ScrollListener,
        GifsListAdapter.Listener {

    public static final String SEARCH_QUERY = "SEARCH_QUERY";

    @Inject
    EventBus bus;

    @Inject
    Contract.Presenter presenter;

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private GifsListAdapter adapter;
    private GridLayoutManager layoutManager;
    private SearchListener searchListener;
    private SearchComponent searchComponent;
    private String searchString;

    private final int INDEX_PROGRESS = 0;
    private final int INDEX_LIST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        adapter = new GifsListAdapter(getActivity(), new ArrayList<Gif>(), this);
        layoutManager = new GridLayoutManager(getActivity(), 3);

        Bundle bundle = getArguments();

        if (bundle != null) {
            searchString = bundle.getString(SEARCH_QUERY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
        presenter.searchGifs(searchString, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.clear();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_list, container, false);
        viewSwitcher = view.findViewById(R.id.view_switcher);
        recyclerView = view.findViewById(R.id.gifs_list);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchListener) {
            searchListener = (SearchListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " + SearchListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchListener = null;
    }

    public interface SearchListener {
        void onItemSelected(Gif item);
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

    protected void init() {
        searchComponent = ComponentHelper.createSearchComponent(GiphyApplication.get(getActivity()).getAppComponent(), this);
        searchComponent.inject(this);
    }

    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemSelected(Gif item) {
        if (searchListener != null) {
            searchListener.onItemSelected(item);
        }
    }

    @Override
    public void setShowLoading() {
        viewSwitcher.setDisplayedChild(INDEX_PROGRESS);
    }

    @Override
    public void showGifs(List<Gif> gifs) {
        viewSwitcher.setDisplayedChild(INDEX_LIST);
        adapter.addData(gifs);
    }

    @Override
    public void showGifDetailsUi(Gif gif) {
        if (searchListener != null) {
            searchListener.onItemSelected(gif);
        }
    }

    @Override
    public void showFailure() {
        Toast.makeText(getActivity(), R.string.trending_gifs_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore(int offset) {
        presenter.searchGifs(searchString, offset);
    }
}
