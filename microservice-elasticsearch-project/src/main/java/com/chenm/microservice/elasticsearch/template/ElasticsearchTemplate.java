package com.chenm.microservice.elasticsearch.template;

import javax.naming.directory.SearchResult;

import org.elasticsearch.client.transport.TransportClient;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ElasticsearchTemplate implements ElasticsearchOperations, ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Autowired
	private TransportClient client;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ElasticsearchTemplate.applicationContext = applicationContext;
	}

	@Override
	public boolean createIndex(String indexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteIndex(String indexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteType(String indexName, String typeName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONObject recommend(SearchResult searchResult, String keyWordsToSearch) {
		
		
		return null;
	}

}
