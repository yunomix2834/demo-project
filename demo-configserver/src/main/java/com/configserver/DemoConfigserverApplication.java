package com.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class DemoConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoConfigserverApplication.class, args);
	}

}
