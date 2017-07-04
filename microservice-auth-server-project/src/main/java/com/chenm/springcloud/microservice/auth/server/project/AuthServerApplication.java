package com.chenm.springcloud.microservice.auth.server.project;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableResourceServer
public class AuthServerApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AuthServerApplication.class, args);
    }
    
    @RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}
