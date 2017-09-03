package com.chenm.microservice.elasticsearch.template;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ElasticsearchTemplate implements ElasticsearchOperations,ApplicationContextAware{

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
	}

}
