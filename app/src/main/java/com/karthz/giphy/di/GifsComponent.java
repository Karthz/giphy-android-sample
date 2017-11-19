package com.karthz.giphy.di;

import com.karthz.giphy.ui.fragment.GifsListFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                GifsModule.class
        }
)
public interface GifsComponent {

    void inject(GifsListFragment fragment);

}
