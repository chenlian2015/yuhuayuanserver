package com.yuhuayuan.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by cl on 2017/3/8.
 */

@Data
public class RequestHeader {
    /**
     * 请求ip地址
     */
    private String ip;
    /**
     * 请求完整地址
     */
    private String uri;
    /**
     * 来源
     */
    private EnumSource source;
    /**
     * 设备号
     */
    private String imei;
    /**
     * 机型
     */
    private String device;
    /**
     * 系统
     */
    private String os;
    /**
     * 系统版本
     */
    @JsonProperty("os_version")
    private String osVersion;
    /**
     * 客户端软件版本
     */
    @JsonProperty("app_version")
    private String appVersion;
    /**
     * 渠道
     */
    @JsonProperty("channel")
    private String channel;
    /**
     * 小区Id
     */
    @JsonProperty("community_id")
    private long communityId;
    /**
     * 请求token
     */
    @JsonProperty("_t")
    private String token;
    /**
     * 请求时间戳
     */
    private long timestamp;
    /**
     * 签名
     */
    private String sig;

    public boolean isIllegal() {
        return source == EnumSource.APP
                && (StringUtils.isAnyBlank(device, os, osVersion, appVersion, channel, sig) || timestamp == 0);
    }

    public EnumDeviceType getDeviceType() {
        return EnumDeviceType.fromDesc(StringUtils.trimToEmpty(getOs()).toLowerCase());
    }

}
