package com.chenm.microservice.elasticsearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Controller
 * @author chenm
 *
 */
//@RestController
@Controller
public class ElasticSearchController {
	
//	@Autowired
//	private TransportClient client;
	
	@RequestMapping("/index")
	public String index(){
		
//		GetResponse response = client.prepareGet("megacorp", "employee", id).get();
//		System.out.println(response);
		
		return "index";
	}
	
	@RequestMapping("/ajax/sug")
	@ResponseBody
	public List<String> sug(){
	    List<String> result = new ArrayList<String>();
	    result.add("aa");
	    result.add("bb");
	    result.add("cc");
		return result;
	}
}
