package com.yuhuayuan.core.persistence;


import com.yuhuayuan.core.dto.user.User;

import java.util.List;


public interface UserMapper {

    int insert(User user);
    
    User selectByOpenid(String openid);
    
    List<User> selectChildUsers(String openid);
}

