package com.yuhuayuan.core.service.goods;

import com.yuhuayuan.core.dto.goods.Goods;

import java.util.List;

/**
 * Created by cl on 2017/3/1.
 */
public interface GoodsService {
    boolean insert(Goods record);
    boolean deleteByPrimaryKey(Integer record);
    List<Goods> getAllGoods();
}
