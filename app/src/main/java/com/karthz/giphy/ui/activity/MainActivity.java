package com.karthz.giphy.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.karthz.giphy.R;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.ui.fragment.GifDetailFragment;
import com.karthz.giphy.ui.fragment.SearchFragment;
import com.karthz.giphy.ui.fragment.TrendingFragment;
import com.karthz.giphy.ui.util.KeyboardUtils;

public class MainActivity extends Activity
        implements TrendingFragment.TrendingListener, SearchFragment.SearchListener {

    private static final String TAG_TRENDING = "TAG_TRENDING";
    private static final String TAG_SEARCH = "TAG_SEARCH";
    private static final String TAG_GIF_DETAIL = "TAG_GIF_DETAIL";

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setOnCloseListener(onCloseListener);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_view, new TrendingFragment(), TAG_TRENDING)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onItemSelected(Gif item) {
        GifDetailFragment fragment = new GifDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GifDetailFragment.GIF_URL, item.getOriginalUrl());
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), TAG_GIF_DETAIL);
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            enterSearchUi(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            return false;
        }
    };

    private SearchView.OnCloseListener onCloseListener = () -> {
        exitSearchUi();
        return false;
    };

    @Override
    public void onBackPressed() {
        if (isInSearchUi()) {
            exitSearchUi();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isInSearchUi() {
        return (getFragmentManager().findFragmentByTag(TAG_SEARCH) != null);
    }

    @UiThread
    private void enterSearchUi(String query) {
        KeyboardUtils.hideKeyboard(searchView);
        FragmentManager fragmentManager = getFragmentManager();
        SearchFragment fragment = (SearchFragment) fragmentManager.findFragmentByTag(TAG_SEARCH);

        if (fragment == null) {
            fragment = new SearchFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(SearchFragment.SEARCH_QUERY, query);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, fragment, TAG_SEARCH)
                .commit();
    }

    @UiThread
    private void exitSearchUi() {
        FragmentManager fragmentManager = getFragmentManager();
        TrendingFragment fragment = (TrendingFragment) fragmentManager.findFragmentByTag(TAG_TRENDING);

        if (fragment == null) {
            fragment = new TrendingFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_view, fragment, TAG_TRENDING)
                .commit();
    }
}
