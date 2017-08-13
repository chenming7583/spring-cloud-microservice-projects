package com.chenm.microservice.elasticsearch.project;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * elasticsearch api 测试用例
 * @author chenm
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  //SpringJUnit支持，由此引入Spring-Test框架支持
@SpringBootTest(classes=ElasticSearchApplication.class) //指定SpringBoot工程的Application启动类 
//@SpringApplicationConfiguration(classes=ElasticSearchApplication.class) //spring boot 1.4.0 前版本
public class ElasticSearchApplicationTest 
{
	@Autowired
	private TransportClient client;
	
	private String indexName = "test";
	
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
		client.prepareDelete().setIndex(indexName).setType("type").execute().actionGet();
	}
	
	/**
	 * 定义索引的映射类型(mapping)
	 * http://blog.csdn.net/eff666/article/details/52432181
	 */
	@Test
	public void defineIndexTypeMapping(){
		try {
			XContentBuilder mapBuilder = XContentFactory.jsonBuilder();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	@Ignore
	public void add(){
		
	}
}
