package com.vpaliy.xyzreader.domain;

import java.util.List;
import rx.Observable;

public interface IRepository<T> {
    Observable<List<T>> get();
    Observable<T> get(int id);
}
