package com.example.main_w.weekly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class weeklyRetrofitFactory_weather {
    private static String BASE_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/";

    public static weeklyRetrofitService_weather getWeekly_weatherApi(){
        return getWeatherInstance().create(weeklyRetrofitService_weather.class);
    }

    private static Retrofit getWeatherInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
