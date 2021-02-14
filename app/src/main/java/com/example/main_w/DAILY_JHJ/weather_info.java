package com.example.main_w.DAILY_JHJ;

public class weather_info {
    String temperature;
    String time;
    String rain_rate;

    public weather_info(String temperature, String time, String rain_rate) {
        this.temperature = temperature;
        this.time = time;
        this.rain_rate = rain_rate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRain_rate() {
        return rain_rate;
    }

    public void setRain_rate(String rain_rate) {
        this.rain_rate = rain_rate;
    }
}
