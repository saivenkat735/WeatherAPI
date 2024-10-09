package com.ust.WeatherAPI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService() {
        this.webClient = WebClient.create();
    }

    public Mono<CityCoordinates> getCoordinatesByCity(String city) {
        String geocodingUrl = "https://nominatim.openstreetmap.org/search?q={city}&format=json&limit=1"
                .replace("{city}", city);

        return webClient.get()
                .uri(geocodingUrl)
                .retrieve()
                .bodyToMono(CityCoordinates[].class)
                .flatMap(coordinatesArray -> {
                    if (coordinatesArray.length > 0) {
                        return Mono.just(coordinatesArray[0]);
                    } else {
                        return Mono.error(new RuntimeException("No coordinates found for the city"));
                    }
                });
    }

    public Mono<String> getWeatherByCity(String city) {
        return getCoordinatesByCity(city).flatMap(coordinates -> {
            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&hourly=temperature_2m"
                    .replace("{lat}", String.valueOf(coordinates.getLat()))
                    .replace("{lon}", String.valueOf(coordinates.getLon()));

            return webClient.get()
                    .uri(weatherUrl)
                    .retrieve()
                    .bodyToMono(WeatherData.class)
                    .map(this::formatWeatherData);
        });
    }

    private String formatWeatherData(WeatherData weatherData) {
        List<String> times = weatherData.getHourly().getTime();
        List<Double> temperatures = weatherData.getHourly().getTemperature_2m();

        StringBuilder formattedWeather = new StringBuilder();
        formattedWeather.append("Weather Data:\n");
        formattedWeather.append("=========================\n");

        for (int i = 0; i < 24; i++) {
            formattedWeather.append("Time: ").append(times.get(i)).append(" | ");
            formattedWeather.append("Temperature: ").append(temperatures.get(i)).append(" °C\n");
        }

        formattedWeather.append("=========================\n");
        return formattedWeather.toString();
    }
}
