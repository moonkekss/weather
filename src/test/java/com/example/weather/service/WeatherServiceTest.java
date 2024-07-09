package com.example.weather.service;

import com.example.weather.config.WeatherApiProperties;
import com.example.weather.model.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
    void getWeatherForecast() {
        String city = "London";
        String apiKey = "testApiKey";
        String url = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric", city,
                apiKey);

        List<ForecastResponse.Forecast> forecastList = new ArrayList<>();
        ForecastResponse.Forecast forecast = new ForecastResponse.Forecast();
        forecast.setName(city);
        ForecastResponse.Main main = new ForecastResponse.Main();
        main.setTemp(15.0);
        forecast.setMain(main);
        ForecastResponse.Weather weather = new ForecastResponse.Weather();
        weather.setDescription("clear sky");
        List<ForecastResponse.Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        forecast.setWeather(weatherList);
        forecastList.add(forecast);

        ForecastResponse mockResponse = new ForecastResponse();
        mockResponse.setList(forecastList);

        when(weatherApiProperties.getKey()).thenReturn(apiKey);
        when(restTemplate.getForObject(url, ForecastResponse.class)).thenReturn(mockResponse);

        ForecastResponse response = weatherService.getWeatherForecast(city);

        assertNotNull(response);
        assertEquals(1, response.getList().size());
        assertEquals(15.0, response.getList().get(0).getMain().getTemp());
        assertEquals("clear sky", response.getList().get(0).getWeather().get(0).getDescription());
    }
}
