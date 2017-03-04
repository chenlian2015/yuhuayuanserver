package com.yuhuayuan.api.controller;

import com.alibaba.fastjson.JSON;
import com.yuhuayuan.core.dto.merchant.MerchantUser;
import com.yuhuayuan.core.service.merchant.MerchantUserService;
import com.yuhuayuan.global.ErrorCode;
import com.yuhuayuan.tool.encrypt.Md5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/2.
 */
@Controller
public class IndexController extends AbstractController{


    @Autowired
    MerchantUserService userService;

    @RequestMapping(value = "/login")
    public
    @ResponseBody
    Map<String, Object> login(@RequestParam(value = "userCode", required = false) String userCode,
                              @RequestParam(value = "password", required = false) String password, HttpServletRequest request,
                              HttpServletResponse response) throws InterruptedException {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(password)) {
            map.put("code", ErrorCode.EC_400004.getCode());
            return map;
        }


        MerchantUser merchantUser = userService.login(userCode, Md5.GetMD5Code(password));
        if (merchantUser == null) {
            map.put("code", ErrorCode.EC_400004.getCode());
            return map;
        }

        long timeStamp = System.currentTimeMillis();

        String complexCode = String.valueOf(timeStamp) + String.valueOf(merchantUser.getMerchantId());

        super.addCookie(response, "merchantUserId", merchantUser.getMerchantId() + "");
        super.addCookie(response, "merchantComplexId", complexCode);
        super.addCookie(response, "merchantSn", Md5.GetMD5Code(complexCode));

        map.put("code", "200");
        return map;

    }

    @RequestMapping(value = "/index.do")
    public ModelAndView adminUserlogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        final String uid = super.getAdminUserId(request);
        if (StringUtils.isEmpty(uid)) {
            mv.setViewName("login");
            return mv;
        }

        MerchantUser userDto = userService.selectByMerchantUserId(uid);
        if (userDto == null) {
            mv.setViewName("login");
            return mv;
        }

        userDto.setId(11111l);
        userService.insert(userDto);

        mv.addObject("merchantViewDto", JSON.toJSONString(userDto));

        mv.setViewName("index");

        return mv;
    }
}
