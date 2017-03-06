package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.version.AppVersion;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
}