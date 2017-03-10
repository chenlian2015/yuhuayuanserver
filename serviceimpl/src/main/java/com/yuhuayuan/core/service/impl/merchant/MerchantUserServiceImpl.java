package com.yuhuayuan.core.service.impl.merchant;

import com.yuhuayuan.core.dto.merchant.MerchantUser;
import com.yuhuayuan.core.dto.systemfunction.SystemFunction;
import com.yuhuayuan.core.persistence.MerchantUserMapper;
import com.yuhuayuan.core.persistence.SystemFunctionMapper;
import com.yuhuayuan.core.service.merchant.MerchantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cl on 2017/3/2.
 */
@Service
public class MerchantUserServiceImpl implements MerchantUserService {
    @Autowired
    MerchantUserMapper merchantUserMapper;

    SystemFunctionMapper systemFunctionMapper;

    public MerchantUser login(String userCode, String pwdMd5ed) {
        return merchantUserMapper.selectByUserCodePwd(userCode, pwdMd5ed);
    }

    public MerchantUser selectByMerchantUserId(String merchant_id) {
        return merchantUserMapper.selectByMerchantUserId(merchant_id);
    }

    public int insert(MerchantUser record) {
        return merchantUserMapper.insert(record);
    }


    public List<SystemFunction> loadUserPrivileges(final Long userId) {
        return systemFunctionMapper.loadUserPrivileges(0);
    }
}
