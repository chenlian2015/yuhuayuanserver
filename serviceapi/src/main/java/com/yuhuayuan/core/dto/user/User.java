package com.yuhuayuan.core.dto.user;

import lombok.Data;

@Data
public class User {
	
    private Integer id;
    
    private String openid;

    private String nickName;
    
    private String headImageUrl;

    private String sharePicWithZCode;
    
    private String shareFromOpenId;
}