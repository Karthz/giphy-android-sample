package com.karthz.giphy.event;

import com.karthz.giphy.model.data.Gif;

import java.util.List;

public class TrendingGifsSuccess {

    private List<Gif> gifs;

    public TrendingGifsSuccess(List<Gif> gifs) {
        this.gifs = gifs;
    }

    public List<Gif> getGifs() {
        return gifs;
    }
}
