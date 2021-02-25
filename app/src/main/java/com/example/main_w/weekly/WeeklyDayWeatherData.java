package com.example.main_w.weekly;

public class WeeklyDayWeatherData {
    String day_text;
    String weather_text;
    String temp_text;
    String rain_prob_text;
    int weather_img;

    public WeeklyDayWeatherData(String day_text, String weather_text, String temp_text, String rain_prob_text, int weather_img) {
        this.day_text = day_text;
        this.weather_text = weather_text;
        this.temp_text = temp_text;
        this.rain_prob_text = rain_prob_text;
        this.weather_img = weather_img;
    }


    public String getDay_text() {
        return day_text;
    }

    public void setDay_text(String day_text) {
        this.day_text = day_text;
    }

    public String getWeather_text() {
        return weather_text;
    }

    public void setWeather_text(String weather_text) {
        this.weather_text = weather_text;
    }

    public String getTemp_text() {
        return temp_text;
    }

    public void setTemp_text(String temp_text) {
        this.temp_text = temp_text;
    }

    public String getRain_prob_text() {
        return rain_prob_text;
    }

    public void setRain_prob_text(String rain_prob_text) {
        this.rain_prob_text = rain_prob_text;
    }

    public int getWeather_img() {
        return weather_img;
    }

    public void setWeather_img(int weather_img) {
        this.weather_img = weather_img;
    }
}
