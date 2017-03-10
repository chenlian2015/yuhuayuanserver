package com.yuhuayuan.core.component.interceptor;

import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.databinder.DataBinderManager;
import com.yuhuayuan.model.RequestHeader;
import com.yuhuayuan.tool.ResponseWriter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInteceptor extends HandlerInterceptorAdapter {

	//@Setter
	//private UserSelectedCommunityService userSelectedCommunityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final User user = DataBinderManager.<User>getDataBinder(Constant.REQUEST_USER_BINDER).get();

		if (user == null) {
			ResponseWriter.writeJsonResponse(response, request.getAttribute(Constant.PASSPORT_ERROR_MSG_KEY));
			return false;
		}

		saveUserLastCommunityId(user.getUid());

		return super.preHandle(request, response, handler);
	}

	private void saveUserLastCommunityId(final long uid) {
		final long communityId = DataBinderManager.<RequestHeader>getDataBinder(Constant.REQUEST_HEADER_BINDER).get().getCommunityId();
		if (communityId > 0) {
		//	userSelectedCommunityService.saveUserLastCommunityId(uid, communityId);
		}
	}
}
