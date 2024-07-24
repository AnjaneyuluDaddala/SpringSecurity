package com.example.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "swagger application",
		version = "1.0",
		description = "about swagger",
		termsOfService = "runcode",
		contact = @Contact(
            name = "anjan",
			email = "anjan01@gmail.com"
		),
		license = @License(
			name = "license",
			url="http://localhost:5000/swagger/"
		)
	),
	servers=@Server(url = "http://localhost:5000/swagger/list")
		
	)
public class SwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerApplication.class, args);
	}

}
