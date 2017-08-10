package com.chenm.microservice.elasticsearch.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.chenm.microservice.elasticsearch.config.ElasticsearchConfiguration;

/**
 * elastic search start
 * @author chenm
 *
 */
@EnableAutoConfiguration
@ComponentScan(basePackageClasses=ElasticsearchConfiguration.class)
@EnableScheduling
@SpringBootApplication
//@EnableEurekaClient
public class ElasticSearchApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
