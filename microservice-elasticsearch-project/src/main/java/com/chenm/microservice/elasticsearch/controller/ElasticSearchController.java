package com.chenm.microservice.elasticsearch.controller;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller
 * @author chenm
 *
 */
//@RestController
@Controller
public class ElasticSearchController {
	
	@Autowired
	private TransportClient client;
	
	@RequestMapping("/index/{id}")
	public String index(@PathVariable String id){
		
//		GetResponse response = client.prepareGet("megacorp", "employee", id).get();
//		System.out.println(response);
		
		return "index";
	}
}
