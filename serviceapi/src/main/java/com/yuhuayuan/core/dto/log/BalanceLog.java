package com.yuhuayuan.core.dto.log;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
public class BalanceLog {
    private Integer id;

    private Integer yuhuayuanid;

    private BigDecimal balancenum;

    private String manager;

    private String balancecode;

    private Date insertdate;

    private Integer balancetype;

    private Map<String,String> otherinfo;
}