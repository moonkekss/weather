package com.example.weather.controller;

import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private Model model;

    @InjectMocks
    private WeatherController weatherController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void home() throws Exception {
        String viewName = weatherController.home();
        assertEquals("index", viewName);

        // Using mockMvc to test the controller method
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getWeather() throws Exception {
        String city = "London";
        WeatherResponse mockResponse = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(15.0);
        mockResponse.setMain(main);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setDescription("clear sky");
        mockResponse.setWeather(new WeatherResponse.Weather[] { weather });

        when(weatherService.getWeather(city)).thenReturn(mockResponse);

        String viewName = weatherController.getWeather(city, model);
        assertEquals("index", viewName);
        verify(model).addAttribute("weather", mockResponse);

        // Using mockMvc to test the controller method
        mockMvc.perform(get("/weather").param("city", city))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
