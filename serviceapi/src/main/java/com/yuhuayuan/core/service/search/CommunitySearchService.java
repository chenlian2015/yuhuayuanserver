package com.yuhuayuan.core.service.search;

import com.yuhuayuan.core.dto.search.CommunitySearchResponse;

import java.util.List;

/**
 * Created by cl on 2017/3/11.
 */

public interface CommunitySearchService {
    /**
     * 重建索引
     */
    void rebuildAll();

    /**
     * 创建索引
     *
     * @param communityId
     */
    void index(long communityId);

    /**
     * 删除索引
     *
     * @param communityId
     */
    void delete(long communityId);

    /**
     * 获取附近的小区
     *
     * @param lat
     * @param lon
     * @param cityId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<CommunitySearchResponse> nearbyCommunity(double lat, double lon, long cityId, int pageNo, int pageSize);

    /**
     * 根据城市id和关键字搜索
     *
     * @param cityId
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return 根据匹配度排序
     */
    List<CommunitySearchResponse> search(long cityId, String keyword, int pageNo, int pageSize);

}
