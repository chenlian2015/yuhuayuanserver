package com.yuhuayuan.core.service.merchant;

import com.yuhuayuan.core.dto.merchant.MerchantUser;

/**
 * Created by cl on 2017/3/2.
 */
public interface MerchantUserService {
    MerchantUser login(String userCode, String pwdMd5ed);

    MerchantUser selectByMerchantUserId(String merchant_id);

    int insert(MerchantUser merchantUser);
}
