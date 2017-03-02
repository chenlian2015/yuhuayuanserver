package com.yuhuayuan.core.persistence;

import com.yuhuayuan.core.dto.goods.Goods;

import java.util.List;


public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    
    List<Goods> getAllGoods();
}