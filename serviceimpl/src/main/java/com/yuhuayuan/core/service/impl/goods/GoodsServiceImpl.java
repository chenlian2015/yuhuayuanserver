package com.yuhuayuan.core.service.impl.goods;

import com.yuhuayuan.core.dto.goods.Goods;
import com.yuhuayuan.core.persistence.GoodsMapper;
import com.yuhuayuan.core.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{

	@Autowired
    protected GoodsMapper goodsMapperObj;
	
	 public boolean insert(Goods record)
	 {
		 goodsMapperObj.insert(record);
		 return true;
	 }
	 
	 public List<Goods> getAllGoods()
	 {
		 return goodsMapperObj.getAllGoods();
	 }
	 
	 public boolean deleteByPrimaryKey(Integer record)
	 {
		 goodsMapperObj.deleteByPrimaryKey(record);
		 return true;
	 }
}
