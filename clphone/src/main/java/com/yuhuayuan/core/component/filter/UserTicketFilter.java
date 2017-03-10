package com.yuhuayuan.core.component.filter;

import com.yuhuayuan.api.model.UserPassport;
import com.yuhuayuan.api.service.user.UserPassportService;
import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.component.encrypt.TokenSupporter;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.api.service.user.UserService;
import com.yuhuayuan.databinder.DataBinderManager;
import com.yuhuayuan.entity.CommonResponse;
import com.yuhuayuan.model.RequestHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by cl on 2017/3/8.
 */
@Component
public class UserTicketFilter implements Filter {

    @Autowired
    private UserPassportService userPassportService;
    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Optional<CommonResponse> responseOptional = doFilterInternal();
        responseOptional.ifPresent(commonResponse -> request.setAttribute(Constant.PASSPORT_ERROR_MSG_KEY, commonResponse));

        chain.doFilter(request, response);
    }

    private Optional<CommonResponse> doFilterInternal() throws IOException {
        final String token = DataBinderManager.<RequestHeader>getDataBinder(Constant.REQUEST_HEADER_BINDER).get().getToken();
        if (StringUtils.isBlank(token)) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_ERR_EMPTY_TICKET + "", Constant.ACK_ERR_EMPTY_TICKET_MSG));
        }

        final long uid = TokenSupporter.decryptUid(token);
        if (uid <= 0) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_ERR_INVALID_TICKET + "", Constant.ACK_ERR_INVALID_TICKET_MSG));
        }

        final Optional<UserPassport> passportOptional = userPassportService.getByUid(uid);
        if (!passportOptional.isPresent()) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_ERR_INVALID_TICKET + "", Constant.ACK_ERR_INVALID_TICKET_MSG));
        }

        final UserPassport passport = passportOptional.get();
        if (!token.equals(passport.getToken())) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_USER_WAS_LOGIN_BY_OTHER_DEVICE + "", Constant.ACK_USER_WAS_LOGIN_BY_OTHER_DEVICE_MSG));
        }

        if (passport.getExpireTime() < System.currentTimeMillis()) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_ERR_EXPIRE_TICKET + "", Constant.ACK_ERR_EXPIRE_TICKET_MSG));
        }

        final Optional<User> userOptional = userService.getByUid(uid);
        if (!userOptional.isPresent()) {
            return Optional.of(CommonResponse.simpleResponse(Constant.ACK_USER_CANT_NOT_BE_NULL + "", Constant.ACK_USER_CANT_NOT_BE_NULL_MSG));
        }

        final User user = userOptional.get();
        userPassportService.touch(user.getUid());
        DataBinderManager.<User>getDataBinder(Constant.REQUEST_USER_BINDER).put(user);

        return Optional.empty();
    }

    @Override
    public void destroy() {

    }
}

