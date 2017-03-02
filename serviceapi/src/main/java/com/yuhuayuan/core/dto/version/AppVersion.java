package com.yuhuayuan.core.dto.version;

import lombok.Data;

import java.util.Date;

@Data
public class AppVersion {
    private Long id;

    private String value;

    private String name;

    private Integer state;

    private Date createTime;

    private Integer plateform;

    private Integer fileId;

    private String fileName;

    private String fileUrl;

    private String versionUrl;

    private String channel;

    private String minvalue;

    private String content;
}