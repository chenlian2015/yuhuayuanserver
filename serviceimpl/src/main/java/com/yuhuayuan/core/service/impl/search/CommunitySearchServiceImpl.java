package com.yuhuayuan.core.service.impl.search;

import com.alibaba.fastjson.JSON;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.yuhuayuan.core.dto.community.Community;
import com.yuhuayuan.core.dto.search.CommunitySearchResponse;
import com.yuhuayuan.core.persistence.CommunityMapper;
import com.yuhuayuan.core.service.search.CommunitySearchService;
import com.yuhuayuan.enums.CommunityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by cl on 2017/3/11.
 */
@Slf4j
@Service
public class CommunitySearchServiceImpl implements CommunitySearchService {

    private static final String ES_COMMUNITY_INDEX = "ejiazi";
    private static final String ES_COMMUNITY_TYPE = "community";
    private static final String COMMUNITY_MAPPING = "{\"properties\":{\"name\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"pinyin\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"cityId\":{\"type\":\"long\"},\"address\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"location\":{\"type\":\"geo_point\",\"lat_lon\":true,\"fielddata\":{\"format\":\"compressed\",\"precision\":\"1m\"}},\"phone\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"intelligentDoorBrand\":{\"type\":\"integer\"},\"status\":{\"type\":\"integer\"}}}";

    @Autowired
    private CommunityMapper communityDao;

    private static String convertToPinyinString(final String str) {
        try {
            return PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            return "";
        }
    }

    @Override
    public void rebuildAll() {
        final Triple<Boolean, String, String> createCommunityIndexResult = SearchHelper.createIndexByAliases(ES_COMMUNITY_INDEX);
        SearchHelper.mapping(createCommunityIndexResult.getMiddle(), ES_COMMUNITY_TYPE, COMMUNITY_MAPPING);

        List<Community> communityEntities;
        communityEntities = communityDao.selectAll();
        if (!CollectionUtils.isEmpty(communityEntities)) {
            final Community lastCommunity = communityEntities.get(communityEntities.size() - 1);

            final BulkRequestBuilder bulkRequestBuilder = SearchHelper.getSearchCilent().prepareBulk();
            communityEntities.forEach(Community -> {
                final CommunityType communityType = new CommunityType(Community);
                final IndexRequestBuilder indexRequestBuilder = SearchHelper.getSearchCilent().prepareIndex(createCommunityIndexResult.getMiddle(), ES_COMMUNITY_TYPE, Community.getId() + "").setSource(JSON.toJSONString(communityType));
                bulkRequestBuilder.add(indexRequestBuilder);
            });

            bulkRequestBuilder.get();
        }


        SearchHelper.changeAliases(ES_COMMUNITY_INDEX, createCommunityIndexResult.getMiddle(), createCommunityIndexResult.getRight());
    }

    @Override
    public void index(final long communityId) {
        final Community Community = communityDao.selectByPrimaryKey(communityId);
        if (Integer.parseInt(Community.getState()) != CommunityEnum.OPENED.getCode()) {
            return;
        }

        final CommunityType communityType = new CommunityType(Community);
        SearchHelper.indexDocumentWithId(ES_COMMUNITY_INDEX, ES_COMMUNITY_TYPE, Community.getId() + "", JSON.toJSONString(communityType));
    }

    @Override
    public void delete(final long communityId) {
        SearchHelper.deleteDocument(ES_COMMUNITY_INDEX, ES_COMMUNITY_TYPE, communityId + "");
    }

    @Override
    public List<CommunitySearchResponse> nearbyCommunity(final double lat, final double lon, final long cityId, final int pageNo, final int pageSize) {
        final BoolQueryBuilder cityFilter = boolQuery().filter(termQuery("cityId", cityId));

        final GeoDistanceSortBuilder locationSort = SortBuilders.geoDistanceSort("location");
        locationSort.unit(DistanceUnit.KILOMETERS);
        locationSort.order(SortOrder.ASC);
        locationSort.point(lat, lon);

        final SearchResponse searchResponse = SearchHelper.getSearchCilent().prepareSearch(ES_COMMUNITY_INDEX)
                .setTypes(ES_COMMUNITY_TYPE)
                .setQuery(cityFilter)
                .setFrom((pageNo - 1) * pageSize)
                .setSize(pageSize)
                .addSort(locationSort)
                .get();

        final long totalHits = searchResponse.getHits().getTotalHits();
        final List<CommunitySearchResponse> pageData = new ArrayList<>();
        final List<CommunitySearchResponse> data = new ArrayList<>();
        searchResponse.getHits().forEach(searchHit -> {
            final CommunityType communityType = JSON.parseObject(searchHit.getSourceAsString(), CommunityType.class);
            final Object[] sortValues = searchHit.getSortValues();

            final CommunitySearchResponse communitySearchResponse = CommunitySearchResponse.builder()
                    .id(communityType.getId())
                    .name(communityType.getName())
                    .address(communityType.getAddress())
                    .distance((Double) sortValues[0])
                    .lat(communityType.getLocation().getLat())
                    .lon(communityType.getLocation().getLon())
                    .intelligentDoorBrand(communityType.getIntelligentDoorBrand())
                    .phone(communityType.getPhone())
                    .build();
            data.add(communitySearchResponse);
        });
        pageData.addAll(data);
        return pageData;
    }

    @Override
    public List<CommunitySearchResponse> search(final long cityId, final String keyword, final int pageNo, final int pageSize) {
        final BoolQueryBuilder cityFilter = boolQuery().filter(termQuery("cityId", cityId));
        final String _keyword = keyword.replace(" ", "").toLowerCase();
        final String pinyin = convertToPinyinString(_keyword);

        DisMaxQueryBuilder disMaxQueryBuilder = disMaxQuery()
                .add(prefixQuery("name", _keyword).boost(2))
                .add(prefixQuery("pinyin", pinyin).boost(2))
                .add(wildcardQuery("name", "*" + _keyword + "*"))
                .add(wildcardQuery("pinyin", "*" + pinyin + "*"))
                .tieBreaker(0.1f);

        final BoolQueryBuilder queryBuilder = cityFilter.must(disMaxQueryBuilder);

        final SearchResponse searchResponse = SearchHelper.getSearchCilent().prepareSearch(ES_COMMUNITY_INDEX)
                .setTypes(ES_COMMUNITY_TYPE)
                .setQuery(queryBuilder)
                .setFrom((pageNo - 1) * pageSize)
                .setSize(pageSize)
                .get();

        final long totalHits = searchResponse.getHits().getTotalHits();

        final List<CommunitySearchResponse> pageData = new ArrayList<>();
        final List<CommunitySearchResponse> data = new ArrayList<>();
        searchResponse.getHits().forEach(searchHit -> {
            final CommunityType communityType = JSON.parseObject(searchHit.getSourceAsString(), CommunityType.class);

            final CommunitySearchResponse communitySearchResponse = CommunitySearchResponse.builder()
                    .id(communityType.getId())
                    .name(communityType.getName())
                    .address(communityType.getAddress())
                    .lat(communityType.getLocation().getLat())
                    .lon(communityType.getLocation().getLon())
                    .intelligentDoorBrand(communityType.getIntelligentDoorBrand())
                    .phone(communityType.getPhone())
                    .build();
            data.add(communitySearchResponse);
        });
        pageData.addAll(data);
        return pageData;
    }

    @Data
    @NoArgsConstructor
    private static final class CommunityType {
        /**
         * communityId
         */
        private long id;
        private String name;
        private String pinyin;
        private long cityId;
        private String address;
        private Location location;
        private String phone;
        private int intelligentDoorBrand;
        /**
         * @see CommunityEnum
         */
        private int status;

        CommunityType(Community Community) {
            this.id = Community.getId();
            this.name = Community.getName().toLowerCase();
            this.pinyin = convertToPinyinString(Community.getName());
            this.cityId = Community.getCityId();
            this.address = Community.getAddress();
            this.location = new Location(NumberUtils.toDouble(Community.getLat()), NumberUtils.toDouble(Community.getLon()));
            this.phone = Community.getPhone();
            this.intelligentDoorBrand = Community.getIntelligenceDoorFactory();
            this.status = Integer.parseInt(Community.getState());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static final class Location {
        private double lat;
        private double lon;
    }

}

