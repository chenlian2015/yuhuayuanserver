package com.yuhuayuan.core.component.filter;


import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.component.encrypt.TokenSupporter;
import com.yuhuayuan.core.component.filter.utils.AppCompatibleUtils;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.databinder.DataBinderManager;
import com.yuhuayuan.model.RequestHeader;
import com.yuhuayuan.tool.json.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cl on 2017/3/14.
 */
@Slf4j(topic = "access")
public class AccessLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (AppCompatibleUtils.getAppIntVersion((HttpServletRequest) request) < 50800) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);

        final int status = ((HttpServletResponse) response).getStatus();
        final RequestHeader requestHeader = DataBinderManager.<RequestHeader>getDataBinder(Constant.REQUEST_HEADER_BINDER).get();
        final String body = DataBinderManager.<String>getDataBinder(Constant.REQUEST_BODY_BINDER).get();
        final long uid = getUid(requestHeader);
        final AccessLog accessLog = new AccessLog(status, uid, requestHeader, body);

        log.info(JsonUtils.toJsonString(accessLog));
    }

    @Override
    public void destroy() {

    }

    private long getUid(final RequestHeader requestHeader) {
        final User user = DataBinderManager.<User>getDataBinder(Constant.REQUEST_USER_BINDER).get();
        if (user != null) {
            return user.getUid();
        }

        if (StringUtils.isBlank(requestHeader.getToken())) {
            return 0;
        }
        return TokenSupporter.decryptUid(requestHeader.getToken());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AccessLog {
        private int status;
        private long uid;
        private RequestHeader header;
        private String body;
    }
}
