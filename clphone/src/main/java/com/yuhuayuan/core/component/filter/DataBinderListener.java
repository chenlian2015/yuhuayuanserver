package com.yuhuayuan.core.component.filter;

import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.databinder.DataBinderManager;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by cl on 2017/3/14.
 */
public class DataBinderListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        DataBinderManager.getDataBinder(Constant.REQUEST_HEADER_BINDER).remove();
        DataBinderManager.getDataBinder(Constant.REQUEST_BODY_BINDER).remove();
        DataBinderManager.getDataBinder(Constant.REQUEST_USER_BINDER).remove();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}
