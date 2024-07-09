package com.example.weather.controller;

import com.example.weather.model.ForecastResponse;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class WeatherController {
    private static final Logger logger = Logger.getLogger(WeatherController.class.getName());

    // inject the WeatherService into the WeatherController.
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/forecast")
    public String getWeather(@RequestParam("city") String city, Model model) {
        try {
            ForecastResponse forecastResponse = weatherService.getWeatherForecast(city);
            if (forecastResponse != null && forecastResponse.getList() != null) {
                model.addAttribute("forecast", forecastResponse.getList());
            } else {
                model.addAttribute("error", "No forecast data available");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch weather data: " + e.getMessage());
        }
        return "index";
    }
}
