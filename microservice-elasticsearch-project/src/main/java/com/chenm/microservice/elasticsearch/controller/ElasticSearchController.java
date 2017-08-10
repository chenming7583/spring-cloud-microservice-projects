package com.chenm.microservice.elasticsearch.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller
 * @author chenm
 *
 */
@Controller
public class ElasticSearchController {
	
	@Autowired
	private TransportClient client;
	
	@RequestMapping("/index")
	public String index(){
		
		GetResponse response = client.prepareGet("information", "article", "ssss").get();
		System.out.println(response);
		
		return "index";
	}
}
