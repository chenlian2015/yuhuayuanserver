package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.log.BalanceLog;

import java.util.List;


public interface BalanceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BalanceLog record);

    int insertSelective(BalanceLog record);

    BalanceLog selectByPrimaryKey(Integer id);

    List<BalanceLog> selectByYuHuaYuanID(Integer id);
    
    int updateByPrimaryKeySelective(BalanceLog record);

    int updateByPrimaryKey(BalanceLog record);
}