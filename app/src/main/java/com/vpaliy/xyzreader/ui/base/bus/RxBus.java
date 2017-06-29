package com.vpaliy.xyzreader.ui.base.bus;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

@Singleton
public class RxBus {

    @Inject
    public RxBus(){}

    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    public void send(Object o) {
        bus.accept(o);
    }

    public Flowable<Object> asFlowable() {
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}