package com.yuhuayuan.core.bean;

import com.yuhuayuan.database.Pageable;
import lombok.Data;

/**
 * Created by cl on 2017/3/16.
 */
@Data
public class SearchUserParam extends Pageable {
    private String mobile;
    private String nickname;
    private long communityId;
    private String startTime;
    private String endTime;
}