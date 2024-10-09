package com.ust.WeatherAPI.controller;

import com.ust.WeatherAPI.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/{city}")
    public Mono<String> getWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherByCity(city);
    }
}
