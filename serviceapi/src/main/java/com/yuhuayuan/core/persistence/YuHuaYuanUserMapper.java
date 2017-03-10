package com.yuhuayuan.core.persistence;


import com.yuhuayuan.core.dto.user.YuHuaYuanUser;

import java.util.List;


public interface YuHuaYuanUserMapper {

    int insert(YuHuaYuanUser user);

    YuHuaYuanUser selectByOpenid(String openid);
    
    List<YuHuaYuanUser> selectChildUsers(String openid);
}

