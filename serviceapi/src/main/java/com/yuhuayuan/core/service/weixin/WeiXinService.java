package com.yuhuayuan.core.service.weixin;

import com.yuhuayuan.core.dto.weixin.UserBaseInfo;

/**
 * Created by cl on 2017/3/1.
 */
public interface WeiXinService {
    String DealRequest(String request);
    String getWeiXinAccessToken();
    String weixinMessage(String request);
    UserBaseInfo getWeiXinUserBaseInfo(String openid);
    String getUserQCode(String openid);
}
