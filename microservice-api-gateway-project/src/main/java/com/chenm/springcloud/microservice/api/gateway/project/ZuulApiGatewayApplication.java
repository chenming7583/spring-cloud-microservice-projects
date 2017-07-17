package com.chenm.springcloud.microservice.api.gateway.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 使用@EnableZuulProxy注解激活zuul
 * @EnableZuulProxy注解整合了@EnableCircuitBreaker、@EnableDiscoveryClient，是个组合注解，目的是简化配置
 * @author chenm
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
public class ZuulApiGatewayApplication 
{
    public static void main( String[] args )
    {
       SpringApplication.run(ZuulApiGatewayApplication.class, args);
    }
}
