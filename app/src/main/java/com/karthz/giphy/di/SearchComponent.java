package com.karthz.giphy.di;

import com.karthz.giphy.ui.fragment.SearchFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                SearchModule.class
        }
)
public interface SearchComponent {

    void inject(SearchFragment fragment);

}
