package com.yuhuayuan.core.component.timer;

import com.alibaba.fastjson.JSONObject;
import com.yuhuayuan.core.service.redis.RedisCacheService;
import com.yuhuayuan.tool.net.http.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("testTask") 
public class WeiXinTimer{

	private static final Logger logger = Logger.getLogger(WeiXinTimer.class);   
      
    @Autowired
    protected RedisCacheService cacheService;
    
    @Scheduled(fixedRate=3600000, initialDelay=1000)//第一次服务器启动一秒后执行，以后每过5秒执行一次，具体情况请查看Scheduled的方法  
    public void test() throws Exception {  
    	
    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8db7ede355114b5f&secret=964885daa6d12ea4171098cc5c7b2cd6";
	
		String str = HttpUtils.get4String(url, new HashMap<String, String>());
		
		JSONObject jo = (JSONObject) JSONObject.parse(str);
		String access_token = (String)jo.get("access_token");
		
		cacheService.set("access_token", access_token);
		
		
		String test = cacheService.get("access_token");
		logger.error("定时任务测试:"+access_token);  
    }  


}
