package com.springmicroservices.springmicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringmicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmicroservicesApplication.class, args);
	}

}
