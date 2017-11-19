package com.karthz.giphy.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.karthz.giphy.model.data.Gif;
import com.karthz.giphy.ui.fragment.GifDetailFragment;
import com.karthz.giphy.ui.fragment.GifsListFragment;

public class MainActivity extends Activity implements GifsListFragment.GifsListListener {

    private static final String TAG_GIF_LIST = "TAG_GIF_LIST";
    private static final String TAG_GIF_DETAIL = "TAG_GIF_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new GifsListFragment(), TAG_GIF_LIST)
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
}
