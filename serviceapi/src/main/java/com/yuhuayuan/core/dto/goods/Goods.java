package com.yuhuayuan.core.dto.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {
    private Integer id;

    private String goodsid;

    private String goodsname;

    private BigDecimal goodsorignprice;

    private BigDecimal goodsellprice;

    private String goodsdescribe;

    private String goodsimageurl;

    private Integer goodsstatus;

    private String discount;
}