package com.yuhuayuan.core.component.interceptor;

import com.yuhuayuan.core.dto.merchant.MerchantUser;
import com.yuhuayuan.core.service.merchant.MerchantUserService;
import com.yuhuayuan.tool.encrypt.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInteceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MerchantUserService userService;
	private List<String> excludeUrlPatterns;
	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {


		String uid = this.getAdminUserId(request);
		String complexCode = this.getComplexCode(request);
		String sn = this.getUserSn(request);

		if (StringUtils.isEmpty(complexCode) || StringUtils.isEmpty(sn)) {//未登录
			response.sendRedirect("/clweb/login");
			return false;
		}
		if (!sn.equals(Md5.GetMD5Code(complexCode)) || !complexCode.substring(13).equals(uid)) {
			delLoginCookieByName(request, response, "merchantUserId");
			delLoginCookieByName(request, response, "merchantSn");
			delLoginCookieByName(request, response, "merchantComplexId");
			response.sendRedirect("/ejiazi-merchant/login");
			return false;
		}

		MerchantUser user = userService.selectByMerchantUserId(uid);
		if (user == null ) { //用户不存在
			delLoginCookieByName(request, response, "merchantUserId");
			delLoginCookieByName(request, response, "merchantSn");
			delLoginCookieByName(request, response, "merchantComplexId");
			response.sendRedirect("/ejiazi-merchant/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}


	private String getAdminUserId(HttpServletRequest req) {
		return this.getCookieByName(req, "merchantUserId");
	}

	private String getUserSn(HttpServletRequest req) {
		return this.getCookieByName(req, "merchantSn");
	}

	private String getComplexCode(HttpServletRequest req) {
		return this.getCookieByName(req, "merchantComplexId");
	}

	private String getCookieByName(HttpServletRequest req, String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Cookie[] cookies = req.getCookies();
		String expectedValue = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					if (StringUtils.isEmpty(cookie.getValue())) {
						expectedValue = cookie.getValue();
					}
				}
			}
		}
		return expectedValue;
	}

	private void delLoginCookieByName(HttpServletRequest req, HttpServletResponse res, String name) {
		if (StringUtils.isEmpty(name)) {
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					res.addCookie(cookie);
				}
			}
		}
	}


	public List<String> getExcludeUrlPatterns() {
		return excludeUrlPatterns;
	}

	public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
		this.excludeUrlPatterns = excludeUrlPatterns;
	}
}
