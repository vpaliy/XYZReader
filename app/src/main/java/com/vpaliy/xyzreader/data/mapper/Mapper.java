package com.vpaliy.xyzreader.data.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps entities between layers
 * @param <R>
 * @param <F>
 */
public abstract class Mapper<R,F> {

    /**
     * Maps a fake entity to a real entity
     * @param fake entity that should be mapped
     * @return the result
     */
    public abstract R map(F fake);


    public List<R> map(List<F> fakeList){
        if(fakeList!=null){
            List<R> list=new ArrayList<>(fakeList.size());
            fakeList.forEach(fake->list.add(map(fake)));
            return list;
        }
        return null;
    }
}
