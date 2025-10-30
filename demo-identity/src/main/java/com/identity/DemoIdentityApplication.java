package com.identity;

import com.identity.dto.DemoConfigurationExampleDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@ComponentScans({ @ComponentScan("com.idenity.controller") })
//@EnableJpaRepositories("com.idenity.repository")
//@EntityScan("com.idenity.entity")
@EnableConfigurationProperties(value = {DemoConfigurationExampleDto.class})
public class DemoIdentityApplication {

	static void main(String[] args) {
		SpringApplication.run(DemoIdentityApplication.class, args);
	}

}
