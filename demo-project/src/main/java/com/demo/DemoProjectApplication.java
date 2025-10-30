package com.demo;

import com.demo.dto.DemoConfigurationExampleDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@ComponentScans({ @ComponentScan("com.demo.controller") })
//@EnableJpaRepositories("com.demo.repository")
//@EntityScan("com.demo.entity")
@EnableConfigurationProperties(value = {DemoConfigurationExampleDto.class})
public class DemoProjectApplication {

	static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

}
