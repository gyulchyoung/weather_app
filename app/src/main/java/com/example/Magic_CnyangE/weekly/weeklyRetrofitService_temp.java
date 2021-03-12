package com.example.Magic_CnyangE.weekly;

import com.example.Magic_CnyangE.weekly.temp_model.WeeklyModel_Temp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weeklyRetrofitService_temp {
    @GET("getMidTa")
    Call<WeeklyModel_Temp> getList(@Query("serviceKey") String serviceKey,
                                   @Query("numOfRows") String numOfRows,
                                   @Query("regId") String regId,
                                   @Query("tmFc") String tmFc,
                                   @Query("dataType") String dataType);
}
