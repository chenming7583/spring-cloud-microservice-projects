package com.chenm.microservice.elasticsearch.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * elastic search start
 * @author chenm
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ElasticSearchApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ElasticSearchApplication.class, args); 
    }
}
