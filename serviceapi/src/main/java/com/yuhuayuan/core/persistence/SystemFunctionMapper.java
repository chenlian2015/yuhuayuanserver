package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.systemfunction.SystemFunction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SystemFunctionMapper {
    int deleteByPrimaryKey(Long functionId);

    int insert(SystemFunction record);

    int insertSelective(SystemFunction record);

    SystemFunction selectByPrimaryKey(Long functionId);

    int updateByPrimaryKeySelective(SystemFunction record);

    int updateByPrimaryKey(SystemFunction record);

    @Select("select *from system_function where parent_id=#{parentid}")
    List<SystemFunction> loadUserPrivileges(@Param("parentid")int id);
}