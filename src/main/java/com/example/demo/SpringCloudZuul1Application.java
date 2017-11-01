package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy//注解开启Zuul
@SpringCloudApplication//相当于@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker 
public class SpringCloudZuul1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuul1Application.class, args);
	}
}
