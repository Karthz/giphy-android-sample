package com.karthz.giphy.util;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Scheduler {

    @NonNull
    public io.reactivex.Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    public io.reactivex.Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    public io.reactivex.Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
