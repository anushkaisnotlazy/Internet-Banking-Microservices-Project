package com.anushka.eurekaserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserviceregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserviceregistryApplication.class, args);
	}

}
