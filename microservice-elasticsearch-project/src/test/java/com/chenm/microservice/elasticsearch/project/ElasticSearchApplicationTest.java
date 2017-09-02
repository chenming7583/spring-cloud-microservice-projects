package com.chenm.microservice.elasticsearch.project;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.directory.SearchResult;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

/**
 * elasticsearch api 测试用例
 * @author chenm
 *
 * 文档介绍
 * http://blog.csdn.net/eff666/article/details/52432181
 * http://www.cnblogs.com/luxiaoxun/archive/2015/10/11/4869509.html
 * http://ginobefunny.com/post/search_recommendation_implemention_based_elasticsearch/ 推荐系统
 */
@RunWith(SpringJUnit4ClassRunner.class)  //SpringJUnit支持，由此引入Spring-Test框架支持
@SpringBootTest(classes=ElasticSearchApplication.class) //指定SpringBoot工程的Application启动类 
//@SpringApplicationConfiguration(classes=ElasticSearchApplication.class) //spring boot 1.4.0 前版本
public class ElasticSearchApplicationTest 
{
	@Autowired
	private TransportClient client;
	
	private String indexName = "indexTest";
	
	private String typeName = "typeTest";
	
	/**
	 * 集群状态
	 */
	@Test
	@Ignore
    public void clusterStatus(){
        //注意集群的client获取方式略有不同，
        ClusterAdminClient clusterAdminClient = client.admin().cluster();
        ClusterHealthResponse healths = clusterAdminClient.prepareHealth().get();
        String clusterName = healths.getClusterName();
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        int numberOfNodes = healths.getNumberOfNodes();
        ClusterHealthStatus status = healths.getStatus();
        System.out.println(clusterName+"###"+numberOfDataNodes+"###"+status.name());

    }
	
	/**
	 * 创建索引
	 */
	@Test
	@Ignore
	public void createIndex(){
		client.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();
	}
	
	/**
	 * 删除索引
	 */
	@Test
	@Ignore
	public void deleteIndex(){
		IndicesExistsResponse indicesExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(new String[]{ indexName })).actionGet();
		if(indicesExistsResponse.isExists()){
			client.admin().indices().delete(new DeleteIndexRequest(indexName)).actionGet();
		}
	}
	
	/**
	 * 删除索引(index)下某个类型(type)
	 */
	@Test
	@Ignore
	public void deleteType(){
		client.prepareDelete().setIndex(indexName).setType(typeName).execute().actionGet();
	}
	
	/**
	 * 定义索引的映射类型(mapping)
	 * http://blog.csdn.net/eff666/article/details/52432181
	 * http://www.cnblogs.com/luxiaoxun/archive/2015/10/11/4869509.html
	 */
	@Test
	@Ignore
	public void defineIndexTypeMapping(){
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject()
	                .startObject("blog")
	                .startObject("properties")
	                .startObject("id").field("type", "integer").field("store", "yes").endObject()
	                .startObject("title").field("type", "string").field("store", "yes").endObject()
	                .startObject("content").field("type", "string").field("store", "yes").endObject()
	                .endObject()
	                .endObject()
	                .endObject();
			 PutMappingRequest mappingRequest = Requests.putMappingRequest("test").type("blog").source(builder);
		        client.admin().indices().putMapping(mappingRequest).actionGet();

					
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
     * 创建数据
     * 
     */
	@Test
	@Ignore
    public void createData() {
        try {
			// 创建数据json 注意此ID是一个字段不是上面查询的id
			   XContentBuilder builder = XContentFactory.jsonBuilder()
			           .startObject()
			           .field("id", "2")
			           .field("title", "我是标题")
			           .field("content", "我是内容")
			           .endObject();
			IndexResponse indexResponse = client.prepareIndex("test","blog").setSource(builder).execute().actionGet();
			System.out.println(indexResponse.status());
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
     * 通过ID查询数据，
     * test为索引库，blog为类型，id为标识
     */
	@Test
	@Ignore
    public void get(){
		GetResponse getResponse = client.prepareGet("test", "blog", "2").get();
        System.out.println(getResponse);  
    }
	/**
	 *  QueryBuilders.inQuery("attr1", new String[{"value1","value2","value3"});
	 *	QueryBuilders.rangeQuery("attr1").gt("value1")
	 */
	@Test
	@Ignore
	public void query(){
	 SearchResponse actionGet = client.prepareSearch("indexName")
                 .setTypes("typeName")
                 .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("attr1", "value1"))
                  ).execute().actionGet();
     SearchHits hits = actionGet.getHits();
     List<Map<String, Object>> matchRsult = new LinkedList<Map<String, Object>>();
     for (SearchHit hit : hits.getHits())
     {
         matchRsult.add(hit.getSource());
     }
	}
	
}
