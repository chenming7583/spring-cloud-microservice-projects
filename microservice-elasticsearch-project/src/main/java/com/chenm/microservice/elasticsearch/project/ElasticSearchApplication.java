package com.chenm.microservice.elasticsearch.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * elastic search start
 * @author chenm
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.chenm.microservice.elasticsearch")
@EnableScheduling
//@EnableEurekaClient
public class ElasticSearchApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
