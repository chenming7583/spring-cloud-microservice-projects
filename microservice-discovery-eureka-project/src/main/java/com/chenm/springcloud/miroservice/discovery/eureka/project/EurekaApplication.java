package com.chenm.springcloud.miroservice.discovery.eureka.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 使用Eureka做服务发现.
 * @author chenm
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(EurekaApplication.class, args);
    }
}
