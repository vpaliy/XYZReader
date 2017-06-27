package com.vpaliy.xyzreader.data.scheduler;

import android.support.annotation.NonNull;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * For testing purposes
 */
public class ImmediateScheduler implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler multi() {
        return Schedulers.immediate();
    }
}