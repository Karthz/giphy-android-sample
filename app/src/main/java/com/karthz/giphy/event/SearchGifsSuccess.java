package com.karthz.giphy.event;

import com.karthz.giphy.model.data.Gif;

import java.util.List;

public class SearchGifsSuccess {

    private List<Gif> gifs;

    public SearchGifsSuccess(List<Gif> gifs) {
        this.gifs = gifs;
    }

    public List<Gif> getGifs() {
        return gifs;
    }
}
