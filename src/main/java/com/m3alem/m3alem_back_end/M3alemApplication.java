package com.m3alem.m3alem_back_end;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class M3alemApplication implements WebMvcConfigurer {

	public static void main(String... args) {
		SpringApplication.run(M3alemApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// registry.addMapping("/api/**")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				.allowedHeaders("Origin", "Accept", "Content-Type", "Authorization")
				.exposedHeaders("Authorization");
	}
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
