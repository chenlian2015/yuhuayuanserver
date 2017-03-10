package com.yuhuayuan.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhuayuan.global.Constant;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cl on 2017/3/2.
 */
public class AbstractController {

    public static final Logger LOG = Logger.getLogger(AbstractController.class);
    protected static final ObjectMapper jsonMapper = new ObjectMapper();
    private static int sessionExpire;
    private static String sessionCookies;

    static {
        try {
            Configuration config = new PropertiesConfiguration("config.properties");
            sessionExpire = config.getInt(Constant.PROPKEY_SESSION_EXPIRE);
            sessionExpire *= 60;
            sessionCookies = config.getString("backend.session.cookies");
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取cms adminId.
     *
     * @param req
     * @return
     */
    protected String getAdminUserId(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String id = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("merchantUserId".equals(cookie.getName())) {
                    if (!StringUtils.isEmpty(cookie.getValue())) {
                        id = cookie.getValue();
                    }
                }
            }
        }
        return id;
    }


    protected void addCookie(HttpServletResponse resp, String name, String value) {
        addCookie(resp, name, value, 60 * 60 * 24 * 30, sessionCookies);
    }

    protected void addCookie(HttpServletResponse resp, String name, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        if (!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }
        resp.addCookie(cookie);
    }

    protected void delCookie(HttpServletResponse resp, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(sessionCookies);
        resp.addCookie(cookie);
    }
}
