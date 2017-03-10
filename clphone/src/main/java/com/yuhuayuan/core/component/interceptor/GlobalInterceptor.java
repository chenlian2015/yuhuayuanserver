package com.yuhuayuan.core.component.interceptor;

import com.yuhuayuan.common.ServerErrorCode;
import com.yuhuayuan.entity.CommonResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cl on 2017/3/8.
 */
@ControllerAdvice
public class GlobalInterceptor {

    /**
     * controller全局异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public CommonResponse handleException(final Exception ex) {
        return CommonResponse.simpleResponse(ServerErrorCode.EC_400001.getCode(), ex.getMessage());
    }

    /**
     * 绑定日期处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
