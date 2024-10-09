package com.ust.WeatherAPI.service;

import java.util.List;

public class WeatherData {
    private Hourly hourly;

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public static class Hourly {
        private List<String> time;
        private List<Double> temperature_2m;

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<Double> getTemperature_2m() {
            return temperature_2m;
        }

        public void setTemperature_2m(List<Double> temperature_2m) {
            this.temperature_2m = temperature_2m;
        }
    }
}
