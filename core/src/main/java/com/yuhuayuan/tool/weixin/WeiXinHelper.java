package com.yuhuayuan.tool.weixin;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeiXinHelper {
	
	
	

	
	private static String APPID = "MY";
	public static String APPSECRET = "";
	/**
     * 生成用于获取access_token的Code的Url
     *
     * @param redirectUrl
     * @return
     */
    public String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                             APPID, redirectUrl, "snsapi_userinfo", "xxxx_state");
    }
    

    
    /**
     * 获取请求用户信息的access_token
     *
     * @param code
     * @return
     */
    public static Map<String, String> getUserInfoAccessToken(String code) {
        JSONObject object = null;
        Map<String, String> data = new HashMap();
        try {
            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                                       APPID, APPSECRET, code);
            
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String tokens = EntityUtils.toString(httpEntity, "utf-8");
          
            JSONObject jo = JSON.parseObject(tokens, JSONObject.class);
            
            data.put("openid", jo.get("openid").toString().replaceAll("\"", ""));
            data.put("access_token", jo.get("access_token").toString().replaceAll("\"", ""));
        } catch (Exception ex) {
            
        }
        return data;
    }
    
    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static Map<String, String> getUserInfo(String accessToken, String openId) {
        Map<String, String> data = new HashMap();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        
        JSONObject userInfo = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            
            String response = EntityUtils.toString(httpEntity, "utf-8");
            JSONObject token_gson = new JSONObject();
            userInfo = JSON.parseObject(response, JSONObject.class);
            
            
            data.put("openid", userInfo.get("openid").toString().replaceAll("\"", ""));
            data.put("nickname", userInfo.get("nickname").toString().replaceAll("\"", ""));
            data.put("city", userInfo.get("city").toString().replaceAll("\"", ""));
            data.put("province", userInfo.get("province").toString().replaceAll("\"", ""));
            data.put("country", userInfo.get("country").toString().replaceAll("\"", ""));
            data.put("headimgurl", userInfo.get("headimgurl").toString().replaceAll("\"", ""));
        } catch (Exception ex) {
            
        }
        return data;
    }
}
