package com.yuhuayuan.core.dto.version;

import java.util.Date;


public class AppVersion {
    public Long id;

    public String value;

    public String name;

    public Integer state;

    public Date createTime;

    public Integer plateform;

    public Integer fileId;

    public String fileName;

    public String fileUrl;

    public String versionUrl;

    public String channel;

    public String minvalue;

    public String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}