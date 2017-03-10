package com.yuhuayuan.databinder;

import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by cl on 2017/3/8.
 */
public final class DataBinderManager {
    private static final ConcurrentHashMap<String, DataBinder> BINDER_MAP = new ConcurrentHashMap();

    public static <T> DataBinder<T> getDataBinder(String bindType) {
        Object dataBinder = (DataBinder)BINDER_MAP.get(bindType);
        if(dataBinder == null) {
            ThreadLocalDataBinder bind = new ThreadLocalDataBinder();
            dataBinder = (DataBinder)BINDER_MAP.putIfAbsent(bindType, bind);
            if(dataBinder == null) {
                dataBinder = bind;
            }
        }

        return (DataBinder)dataBinder;
    }

    private DataBinderManager() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
