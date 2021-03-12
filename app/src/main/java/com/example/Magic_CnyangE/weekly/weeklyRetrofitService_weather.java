package com.example.Magic_CnyangE.weekly;

import com.example.Magic_CnyangE.weekly.weather_model.WeeklyModel_weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weeklyRetrofitService_weather {
    @GET("getMidLandFcst")
    Call<WeeklyModel_weather> getList(@Query("serviceKey") String serviceKey,
                                      @Query("numOfRows") String numOfRows,
                                      @Query("regId") String regId,
                                      @Query("tmFc") String tmFc,
                                      @Query("dataType") String dataType);
}
