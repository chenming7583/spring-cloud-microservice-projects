package com.chenm.microservice.elasticsearch.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller
 * @author chenm
 *
 */
@Controller
public class ElasticSearchController {
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
