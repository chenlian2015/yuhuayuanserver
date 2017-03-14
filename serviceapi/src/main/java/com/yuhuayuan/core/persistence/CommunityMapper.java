package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.community.Community;

import java.util.List;

public interface CommunityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Community record);

    int insertSelective(Community record);

    Community selectByPrimaryKey(Long id);

    List<Community> selectAll();

    int updateByPrimaryKeySelective(Community record);

    int updateByPrimaryKey(Community record);
}