package com.yuhuayuan.core.component.filter;

import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.component.filter.utils.AppCompatibleUtils;
import com.yuhuayuan.databinder.DataBinderManager;
import com.yuhuayuan.entity.CommonResponse;
import com.yuhuayuan.model.EnumSource;
import com.yuhuayuan.model.RequestHeader;
import com.yuhuayuan.tool.ResponseWriter;
import com.yuhuayuan.tool.internet.IpUtils;
import com.yuhuayuan.tool.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by cl on 2017/3/8.
 */
@Component
public class RequestHeaderFilter implements Filter {

    @Value("${header.check:false}")
    private boolean checkHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Optional<RequestHeader> requestHeaderOptional = getRequestHeader((HttpServletRequest) request);

        if (AppCompatibleUtils.getAppIntVersion((HttpServletRequest) request) < 50800) {
            requestHeaderOptional.ifPresent(requestHeader -> DataBinderManager.<RequestHeader>getDataBinder(Constant.REQUEST_HEADER_BINDER).put(requestHeader));
            chain.doFilter(request, response);
            return;
        }

        if (!requestHeaderOptional.isPresent()) {
            //log.info("request error, because header is empty");
            ResponseWriter.writeJsonResponse(response, CommonResponse.simpleResponse(Constant.ILLEGAL_REQUEST, "illegal request"));
            return;
        }

        final RequestHeader requestHeader = requestHeaderOptional.get();
        if (checkHeader && requestHeader.isIllegal()) {
            //log.info("request error, because header is illegal. header -> {}", requestHeader);
            ResponseWriter.writeJsonResponse(response, CommonResponse.simpleResponse(Constant.ILLEGAL_REQUEST, "illegal request"));
            return;
        }

        DataBinderManager.<RequestHeader>getDataBinder(Constant.REQUEST_HEADER_BINDER).put(requestHeader);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private Optional<RequestHeader> getRequestHeader(final HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null || !headerNames.hasMoreElements()) {
            return Optional.empty();
        }

        final Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            headerMap.put(headerName.toLowerCase(), StringUtils.trimToEmpty(request.getHeader(headerName)));
        }

        final RequestHeader requestHeader = JsonUtils.parseObject(JsonUtils.toJsonString(headerMap), RequestHeader.class);
        requestHeader.setIp(IpUtils.getIp(request));
        requestHeader.setSource(getRequestSource(request));
        requestHeader.setUri(getRequestURI(request));

        return Optional.of(requestHeader);
    }

    private String getRequestURI(final HttpServletRequest request) {
        final String queryString = request.getQueryString();
        final StringBuffer requestURL = request.getRequestURL();
        return StringUtils.isNotBlank(queryString) ? requestURL.append('?').append(queryString).toString() : requestURL.toString();
    }

    private EnumSource getRequestSource(final HttpServletRequest request) {
        final String referer = request.getHeader("referer");
        return StringUtils.isBlank(referer) ? EnumSource.APP : EnumSource.H5;
    }

}

