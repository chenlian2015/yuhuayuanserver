package com.yuhuayuan.databinder;

/**
 * Created by cl on 2017/3/8.
 */
public class ThreadLocalDataBinder<T> implements DataBinder<T> {
    private final ThreadLocal<T> threadLocal = new ThreadLocal();

    public ThreadLocalDataBinder() {
    }

    public void put(T t) {
        this.threadLocal.set(t);
    }

    public T get() {
        return this.threadLocal.get();
    }

    public void remove() {
        this.threadLocal.remove();
    }
}