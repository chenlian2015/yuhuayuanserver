package com.yuhuayuan.api.controller;

import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.databinder.DataBinder;
import com.yuhuayuan.databinder.DataBinderManager;
import com.yuhuayuan.model.RequestHeader;
import com.yuhuayuan.tool.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by cl on 2017/3/10.
 */
@Slf4j
public abstract class AbstractController {

    private static final DataBinder<User> REQUEST_USER_BINDER = DataBinderManager.getDataBinder(Constant.REQUEST_USER_BINDER);
    private static final DataBinder<RequestHeader> REQUEST_HEADER_BINDER = DataBinderManager.getDataBinder(Constant.REQUEST_HEADER_BINDER);
    private static final DataBinder<String> REQUEST_BODY_BINDER = DataBinderManager.getDataBinder(Constant.REQUEST_BODY_BINDER);

//    @Autowired
//    protected PublicSession publicSession;

    @ModelAttribute
    public void init(ModelMap model, HttpServletRequest request) {
        model.put("ctx", request.getContextPath());
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        model.put("ctxHost", tempContextUrl);
    }

    protected RequestHeader getHeader() {
        return REQUEST_HEADER_BINDER.get();
    }

    protected Map<String, String> getHeaderMap(final HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null || !headerNames.hasMoreElements()) {
            return new HashMap<>();
        }

        final Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            headerMap.put(name, StringUtils.trimToEmpty(request.getHeader(name)));
        }
        return headerMap;
    }

    protected String getBodyAsString() {
        return REQUEST_BODY_BINDER.get();
    }

    protected Map<String, String> getBodyAsMap() {
        final String requestBody = REQUEST_BODY_BINDER.get();
        if (StringUtils.isBlank(requestBody)) {
            return new HashMap<>();
        }

        return JsonUtils.parseObject(requestBody, Map.class);
    }

    protected Map<String, Object> getBodyAsMap2() {
        final String requestBody = REQUEST_BODY_BINDER.get();
        if (StringUtils.isBlank(requestBody)) {
            return new HashMap<>();
        }

        return JsonUtils.parseObject(requestBody, Map.class);
    }

    protected <T> Optional<T> getBodyAsObject(Class<T> clazz) {
        final String requestBody = REQUEST_BODY_BINDER.get();
        if (StringUtils.isBlank(requestBody)) {
            return Optional.empty();
        }
        return Optional.of(JsonUtils.parseObject(requestBody, clazz));
    }

    protected User getLoginUser() {
        return REQUEST_USER_BINDER.get();
    }

    protected long getLoginUid() {
        final User loginUser = getLoginUser();
        return loginUser == null ? 0 : loginUser.getUid();
    }

    /**
     * @deprecated controller中获取用户时，通过拦截器的请求永不一定存在
     * @return
     */
    @Deprecated
    protected boolean isUserExit() {
        return getLoginUser() != null;
    }

    protected long getLoginUserCommunityId() {
        return REQUEST_HEADER_BINDER.get().getCommunityId();
    }

}

