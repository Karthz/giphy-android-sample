package com.karthz.giphy.di;

import com.karthz.giphy.ui.fragment.TrendingFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                TrendingModule.class
        }
)
public interface TrendingComponent {

    void inject(TrendingFragment fragment);

}
