package com.identity;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@ComponentScans({ @ComponentScan("com.idenity.controller") })
//@EnableJpaRepositories("com.idenity.repository")
//@EntityScan("com.idenity.entity")
//@EnableConfigurationProperties(value = {DemoConfigurationExampleDto.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Identity microservice REST API Documentation",
                description = "Microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Yunomi Xavia",
                        email = "yunomix2834@gmail.com",
                        url = "https://github.com/yunomix2834"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/yunomix2834"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "Microservice REST API Documentation",
                url = "https://github.com/yunomix2834"
        )
)
public class DemoIdentityApplication {

	static void main(String[] args) {
		SpringApplication.run(DemoIdentityApplication.class, args);
	}

}
