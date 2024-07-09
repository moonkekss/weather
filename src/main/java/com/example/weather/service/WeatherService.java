package com.example.weather.service;

import com.example.weather.config.WeatherApiProperties;
import com.example.weather.model.ForecastResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.logging.Logger;

@Service
public class WeatherService {
    private static final Logger logger = Logger.getLogger(WeatherService.class.getName());

    private final RestTemplate restTemplate;
    private final WeatherApiProperties weatherApiProperties;

    public WeatherService(RestTemplate restTemplate, WeatherApiProperties weatherApiProperties) {
        this.restTemplate = restTemplate;
        this.weatherApiProperties = weatherApiProperties;
    }

    public ForecastResponse getWeatherForecast(String city) {
        String url = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric", city,
                weatherApiProperties.getKey());
        logger.info("Request URL: " + url);
        ForecastResponse response = null;
        try {
            response = restTemplate.getForObject(url, ForecastResponse.class);
            logger.info("API Response: " + response);
        } catch (Exception e) {
            logger.severe("Error fetching weather data: " + e.getMessage());
        }
        return response;
        // return restTemplate.getForObject(url, ForecastResponse.class);

    }
}
