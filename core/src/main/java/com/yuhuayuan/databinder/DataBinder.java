package com.yuhuayuan.databinder;

/**
 * Created by cl on 2017/3/8.
 */
public interface DataBinder<T> {
    void put(T var1);

    T get();

    void remove();
}
