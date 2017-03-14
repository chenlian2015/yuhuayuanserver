package com.yuhuayuan.core.service.impl.search;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.yuhuayuan.tool.config.Configuration;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;

/**
 * Created by cl on 2017/3/11.
 */
public class SearchHelper {
    private static final int DEFAULT_NUMBER_OF_SHARDS = 5;
    private static final int DEFAULT_NUMBER_OF_REPLICAS = 1;

    /**
     * 获取es client
     *
     * @return
     */
    public static Client getSearchCilent() {
        return ClientHolder.CLIENT;
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public static boolean createIndex(final String index) {
        if (isIndexExists(index)) {
            return false;
        }

        final int shardCount = NumberUtils.toInt(Configuration.getValue("es.index.number_of_shards"), DEFAULT_NUMBER_OF_SHARDS);
        final int replicaCount = NumberUtils.toInt(Configuration.getValue("es.index.number_of_replicas"), DEFAULT_NUMBER_OF_REPLICAS);

        final CreateIndexResponse createIndexResponse = ClientHolder.CLIENT.admin().indices().prepareCreate(index)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", shardCount)
                        .put("index.number_of_replicas", replicaCount))
                .get();

        return createIndexResponse.isAcknowledged();
    }

    /**
     * 根据别名生成索引,但是不绑定,背后的真实索引自动生成
     *
     * @param alias 别名
     * @return Pair: left表示创建是否成功, middle表示新建的索引, right 表示旧的索引
     */
    public static Triple<Boolean, String, String> createIndexByAliases(final String alias) {
        final Optional<String> actualIndexOptional = getActualIndex(alias);
        final String oldIndex = actualIndexOptional.isPresent() ? actualIndexOptional.get() : null;

        final String actualIndex = makeActualIndex(alias);
        return Triple.of(createIndex(actualIndex), actualIndex, oldIndex);
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(final String index) {
        final DeleteIndexResponse deleteIndexResponse = ClientHolder.CLIENT.admin().indices().prepareDelete(index).get();
        return deleteIndexResponse.isAcknowledged();
    }

    /**
     * 给索引指定别名
     *
     * @param index
     * @param alias
     * @return
     */
    public static boolean indicesAliases(final String index, final String alias) {
        final IndicesAliasesResponse indicesAliasesResponse = ClientHolder.CLIENT.admin().indices().prepareAliases().addAlias(index, alias).get();
        return indicesAliasesResponse.isAcknowledged();
    }

    /**
     * 原子的修改索引的索引
     *
     * @param alias
     * @param newInex
     * @param oldIndex
     * @return
     */
    public static boolean changeAliases(final String alias, final String newInex, final String oldIndex) {
        if (isIndexExists(oldIndex)) {
            return ClientHolder.CLIENT.admin().indices().prepareAliases()
                    .removeAlias(oldIndex, alias).addAlias(newInex, alias).get().isAcknowledged();
        } else {
            return ClientHolder.CLIENT.admin().indices().prepareAliases().addAlias(newInex, alias).get().isAcknowledged();
        }
    }


    /**
     * 索引单个文档
     *
     * @param index
     * @param type
     * @param id
     * @param document
     * @return Returns true if the document was created, false if updated.
     */
    public static boolean indexDocumentWithId(final String index, final String type, final String id, final String document) {
        final IndexResponse indexResponse = ClientHolder.CLIENT.prepareIndex(index, type, id).setSource(document).setRefresh(true).get();
        return indexResponse.isCreated();
    }

    /**
     * 删除文档
     *
     * @param index
     * @param type
     * @param id
     * @return Returns true if a doc was found to delete.
     */
    public static boolean deleteDocument(final String index, final String type, final String id) {
        final DeleteResponse deleteResponse = ClientHolder.CLIENT.prepareDelete(index, type, id).get();
        return deleteResponse.isFound();
    }

    /**
     * 根据查询批量删除
     *
     * @param index
     * @param type
     * @param queryBuilder
     * @return
     */
    public static long deleteByQuery(final String index, final String type, final QueryBuilder queryBuilder) {
        final DeleteByQueryResponse deleteByQueryResponse = new DeleteByQueryRequestBuilder(ClientHolder.CLIENT, DeleteByQueryAction.INSTANCE)
                .setIndices(index)
                .setTypes(type)
                .setQuery(queryBuilder)
                .get();

        return deleteByQueryResponse.getTotalDeleted();
    }

    /**
     * 判断文档是否存在
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static boolean exits(final String index, final String type, final String id) {
        return StringUtils.isNotBlank(getDocument(index, type, id));
    }

    /**
     * 根据id获取文档
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static String getDocument(final String index, final String type, final String id) {
        final GetResponse getResponse = ClientHolder.CLIENT.prepareGet(index, type, id).get();
        return getResponse.getSourceAsString();
    }

    /**
     * 设置mapping
     *
     * @param index
     * @param type
     * @param xContentBuilder
     * @return
     */
    public static boolean mapping(final String index, final String type, final XContentBuilder xContentBuilder) {
        return ClientHolder.CLIENT.admin().indices().preparePutMapping(index).setType(type).setSource(xContentBuilder).get().isAcknowledged();
    }

    /**
     * 设置mapping
     *
     * @param index
     * @param type
     * @param mapping
     * @return
     */
    public static boolean mapping(final String index, final String type, final String mapping) {
        return ClientHolder.CLIENT.admin().indices().preparePutMapping(index).setType(type).setSource(mapping).get().isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    private static boolean isIndexExists(final String index) {
        if (StringUtils.isBlank(index)) {
            return false;
        }
        return ClientHolder.CLIENT.admin().indices().prepareExists(index).get().isExists();
    }

    /**
     * 获取指定别名对应的真实索引,只考虑有一个别名的情况
     *
     * @param alias
     * @return
     */
    private static Optional<String> getActualIndex(final String alias) {
        final GetAliasesResponse getAliasesResponse = ClientHolder.CLIENT.admin().indices().prepareGetAliases(alias).get();
        if (getAliasesResponse.getAliases().size() > 0) {
            return Optional.of(getAliasesResponse.getAliases().iterator().next().key);
        }
        return Optional.empty();
    }

    private static String makeActualIndex(final String alias) {
        final Optional<String> actualIndexOptional = getActualIndex(alias);
        if (!actualIndexOptional.isPresent()) {
            return alias + "_v1";
        }

        final String actualIndex = actualIndexOptional.get();
        final int lastVersion = Integer.valueOf(actualIndex.substring(actualIndex.lastIndexOf("_v") + 2));

        return actualIndex.substring(0, actualIndex.lastIndexOf("_v")) + "_v" + (lastVersion + 1);
    }

    private static final class ClientHolder {
        private static final Client CLIENT;

        static {
            final String esHosts = Configuration.getValue("es.host");
            Preconditions.checkArgument(StringUtils.isNoneBlank(esHosts), "es.host not exit in config file");

            final String esClusterName = Configuration.getValue("es.cluster.name");
            Preconditions.checkArgument(StringUtils.isNoneBlank(esClusterName), "es.cluster.name not exit in config file");

            final boolean esClientTransportSniff = BooleanUtils.toBoolean(Configuration.getValue("es.client.transport.sniff"));

            final Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", esClusterName)
                    .put("client.transport.sniff", esClientTransportSniff)
                    .build();

            final TransportClient transportClient = TransportClient.builder().settings(settings).addPlugin(DeleteByQueryPlugin.class).build();

            final Map<String, String> hosts = Splitter.on(',').trimResults().omitEmptyStrings().withKeyValueSeparator(':').split(esHosts);
            for (Map.Entry<String, String> hostEntry : hosts.entrySet()) {
                transportClient.addTransportAddresses(new InetSocketTransportAddress(new InetSocketAddress(hostEntry.getKey(), NumberUtils.toInt(hostEntry.getValue()))));
            }
            transportClient.connectedNodes();

            CLIENT = transportClient;

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                CLIENT.close();
            }));
        }
    }

}
