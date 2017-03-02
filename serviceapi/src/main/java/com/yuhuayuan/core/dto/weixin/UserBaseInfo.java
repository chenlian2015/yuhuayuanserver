package com.yuhuayuan.core.dto.weixin;

import lombok.Data;

@Data
public class UserBaseInfo {
	private String subscribe;
	private String openid;
	private String nickname;
	private String sex;
	private String headimgurl;
	private String subscribe_time;
	private String unionid;
	private String remark;
}
