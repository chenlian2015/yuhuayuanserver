package com.yuhuayuan.api.controller;

import com.yuhuayuan.core.service.weixin.WeiXinService;
import com.yuhuayuan.tool.ControllTool;
import com.yuhuayuan.tool.returngson.GsonResult;
import com.yuhuayuan.tool.weixin.SignUtil;
import com.yuhuayuan.tool.weixin.WeiXinHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;




@Controller
public class WeiXinController {

	private static final Logger logger = Logger.getLogger(WeiXinController.class); 
	
	@Autowired
	WeiXinService weiXinServiceImpl;
	
	@RequestMapping(value = "wxInterfaceSetting.do")
	private void wxInterfaceSetting(HttpServletRequest request, HttpServletResponse response)
	{
		ControllTool.LogRequest(request, logger);
		
		 // 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
        PrintWriter out = null;
		
        if (!SignUtil.checkSignature(signature, timestamp, nonce)) {  
           return;
        }  
        
        try {
        	response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		String strRequest = ControllTool.getRequestBody(request);
		logger.error(strRequest);
		String echo = weiXinServiceImpl.weixinMessage(strRequest);

        out.print(echo);  
        out.close();
        out = null;
	}
	
    public String auth(HttpServletRequest request,
                         String code) {
        Map<String, String> data = new HashMap();
        Map<String, String> result = WeiXinHelper.getUserInfoAccessToken(code);//通过这个code获取access_token
        String openId = result.get("openid");
        if (!StringUtils.isEmpty(openId)) {
            logger.info("try getting user info. [openid={}]");
            Map<String, String> userInfo = WeiXinHelper.getUserInfo(result.get("access_token"), openId);//使用access_token获取用户信息
            logger.info("received user info. [result={}]");
            return "forward(auth, userInfo)";
        }
        return "Response.ok(\"openid为空\").build()";
    }
    
	@RequestMapping(value = "weixinAuth.do" ,  method = RequestMethod.POST)
	private @ResponseBody GsonResult weixinAuth(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
		String strRequest = ControllTool.getRequestBody(request);
		logger.debug(strRequest);
		}catch(Exception e)
		{
			
		}
		return null;
	}
}
