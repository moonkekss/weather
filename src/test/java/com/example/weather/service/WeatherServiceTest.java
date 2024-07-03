package com.example.weather.service;

import com.example.weather.config.WeatherApiProperties;
import com.example.weather.model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherApiProperties weatherApiProperties;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWeather() {
        String city = "London";
        String apiKey = "testApiKey";
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city,
                apiKey);

        WeatherResponse mockResponse = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(15.0);
        mockResponse.setMain(main);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setDescription("clear sky");
        mockResponse.setWeather(new WeatherResponse.Weather[] { weather });

        when(weatherApiProperties.getKey()).thenReturn(apiKey);
        when(restTemplate.getForObject(url, WeatherResponse.class)).thenReturn(mockResponse);

        WeatherResponse response = weatherService.getWeather(city);

        assertNotNull(response);
        assertEquals(15.0, response.getMain().getTemp());
        assertEquals("clear sky", response.getWeather()[0].getDescription());
    }
}
