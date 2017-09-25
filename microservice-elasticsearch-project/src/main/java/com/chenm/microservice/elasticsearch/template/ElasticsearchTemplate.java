package com.chenm.microservice.elasticsearch.template;

import org.elasticsearch.client.transport.TransportClient;
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

}
