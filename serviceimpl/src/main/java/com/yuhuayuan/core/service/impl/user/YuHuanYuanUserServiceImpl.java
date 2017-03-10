package com.yuhuayuan.core.service.impl.user;

import com.yuhuayuan.core.dto.user.YuHuaYuanUser;
import com.yuhuayuan.core.persistence.YuHuaYuanUserMapper;
import com.yuhuayuan.core.service.redis.RedisCacheService;
import com.yuhuayuan.core.service.user.YuHuaYuanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class YuHuanYuanUserServiceImpl implements YuHuaYuanUserService {
	
    @Autowired
    protected YuHuaYuanUserMapper yuHuaYuanUserMapper;
    
    @Autowired
    protected RedisCacheService cacheService;
    
    public boolean insert(YuHuaYuanUser u)
    {
    	Map m = new HashMap<String,Object>();

        YuHuaYuanUser uGet = yuHuaYuanUserMapper.selectByOpenid(u.getOpenid());
    	if(null == uGet)
    	{
    		yuHuaYuanUserMapper.insert(u);
    	}
    	return true;
    }
    
    
}
