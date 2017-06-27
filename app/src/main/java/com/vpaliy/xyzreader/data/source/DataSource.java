package com.vpaliy.xyzreader.data.source;

import com.vpaliy.xyzreader.domain.IRepository;

public abstract  class DataSource<T> implements IRepository<T>{
    public void insert(T item){}
}
