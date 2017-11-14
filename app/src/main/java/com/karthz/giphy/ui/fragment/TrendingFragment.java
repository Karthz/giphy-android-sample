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

import com.karthz.giphy.GiphyApplication;
import com.karthz.giphy.R;
import com.karthz.giphy.di.ComponentHelper;
import com.karthz.giphy.di.TrendingComponent;
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

public class TrendingFragment extends Fragment
        implements Contract.View, EndlessRecyclerViewScrollListener.ScrollListener,
        GifsListAdapter.Listener {

    @Inject
    EventBus bus;

    @Inject
    Contract.Presenter presenter;

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private GifsListAdapter adapter;
    private GridLayoutManager layoutManager;
    private TrendingComponent trendingComponent;
    private TrendingListener trendingListener;

    private final int INDEX_PROGRESS = 0;
    private final int INDEX_LIST = 1;

    public interface TrendingListener {
        void onItemSelected(Gif item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        adapter = new GifsListAdapter(getActivity(), new ArrayList<Gif>(), this);
        layoutManager = new GridLayoutManager(getActivity(), 3);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
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

        if (context instanceof TrendingListener) {
            trendingListener = (TrendingListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement "
                    + TrendingFragment.TrendingListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        trendingListener = null;
    }

    protected void init() {
        trendingComponent = ComponentHelper.createTrendingComponent(GiphyApplication.get(getActivity()).getAppComponent(), this);
        trendingComponent.inject(this);
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

    @Override
    public void onItemSelected(Gif item) {
        if (trendingListener != null) {
            trendingListener.onItemSelected(item);
        }
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
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
        if (trendingListener != null) {
            trendingListener.onItemSelected(gif);
        }
    }

    @Override
    public void showFailure() {
        Toast.makeText(getActivity(), R.string.trending_gifs_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore(int offset) {
        presenter.loadGifs(offset);
    }
}
