package com.example.weather.service;

import com.example.weather.config.WeatherApiProperties;
import com.example.weather.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherApiProperties weatherApiProperties;

    public WeatherService(RestTemplate restTemplate, WeatherApiProperties weatherApiProperties) {
        this.restTemplate = restTemplate;
        this.weatherApiProperties = weatherApiProperties;
    }

    public WeatherResponse getWeather(String city) {
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city,
                weatherApiProperties.getKey());
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
