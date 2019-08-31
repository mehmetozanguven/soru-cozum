package com.myProjects.soru_cozum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SoruCozumApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SoruCozumApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SoruCozumApplication.class);
	}

}
