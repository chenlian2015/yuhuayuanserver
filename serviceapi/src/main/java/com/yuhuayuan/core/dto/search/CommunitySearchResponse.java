package com.yuhuayuan.core.dto.search;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by cl on 2017/3/11.
 */
@Builder
@Getter
public class CommunitySearchResponse {
    /**
     * 小区id
     */
    private long id;
    /**
     * 小区名
     */
    private String name;
    /**
     * 距离，单位km
     */
    private double distance;
    /**
     * 小区地址
     */
    private String address;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lon;
    /**
     * 物业电话
     */
    private String phone;
    /**
     * 智能门禁品牌
     */
    private int intelligentDoorBrand;
}

