package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.weather.config.WeatherApiProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeatherApiProperties.class)
public class WeatherappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherappApplication.class, args);
	}

	// a synchronous client to perform HTTP requests, exposing a simple,
	// Stemplate-based API over underlying HTTP client libraries.
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
