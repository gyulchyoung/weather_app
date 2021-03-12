package com.example.Magic_CnyangE.weekly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class weeklyRetrofitFactory_temp {
    private static String BASE_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/";

    public static weeklyRetrofitService_temp getWeekly_tempApi(){
        return getTempInstance().create(weeklyRetrofitService_temp.class);
    }

    private static Retrofit getTempInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
