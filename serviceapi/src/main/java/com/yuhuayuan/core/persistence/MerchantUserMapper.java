package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.merchant.MerchantUser;
import org.apache.ibatis.annotations.Param;

public interface MerchantUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantUser record);

    MerchantUser selectByUserCodePwd(@Param("userCode")String userCode, @Param("password")String password);

    int insertSelective(MerchantUser record);

    MerchantUser selectByMerchantUserId(String merchant_id);

    int updateByPrimaryKeySelective(MerchantUser record);

    int updateByPrimaryKey(MerchantUser record);
}