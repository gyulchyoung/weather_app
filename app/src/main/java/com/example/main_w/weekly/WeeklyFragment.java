package com.example.main_w.weekly;

//import android.support.v7.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.PreferenceManager;
import com.example.main_w.R;
import com.example.main_w.weekly.temp_model.WeeklyModel_Temp;
import com.example.main_w.weekly.weather_model.Item;
import com.example.main_w.weekly.weather_model.WeeklyModel_weather;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeeklyFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    WeeklyMainAdapter adapter;
    String API_KEY;
    String weather;
    WeeklyDayWeatherData data;
    String[] dayarr;
    int day;



    {
        try {
            API_KEY = URLDecoder.decode("VQ0keALnEea3BkQdEGgwgCD8XNDNR%2Fg98L9D4GzWryl4UYHnGfUUUI%2BHDA6DdzYjjzJmuHT1UmuJZ7wJHoGfuA%3D%3D", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.weekly_recycler, container, false);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        String now_string = time_f.format(calendar.getTime());
        Date now_date = null;
        try {
            now_date = time_f.parse(now_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String sixAMstring = "06:00:00";
        Date sixAM = null;
        try {
            sixAM = time_f.parse(sixAMstring);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String set_time="0600";
        SimpleDateFormat date_f = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        if (now_date.before(sixAM)) {
            calendar.add(Calendar.DATE, -1 );  // 오늘 날짜에서 하루를 뺌.
            set_time="1800";
        }
        String date = date_f.format(calendar.getTime());

        day = calendar.get(Calendar.DAY_OF_WEEK);


        System.out.println(date);
        System.out.println(now_string);
        System.out.println(sixAMstring);



        Resources resources=getResources();
        dayarr = resources.getStringArray(R.array.days);

        recyclerView=rootView.findViewById(R.id.weekly_recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeeklyMainAdapter();
        recyclerView.setAdapter(adapter);

        initData();

        String tempCode = PreferenceManager.getString(getContext(), "locationCode");
        String weatherCode = PreferenceManager.getString(getContext(), "locationCountryCode");


        Call<WeeklyModel_weather> getWeatherInstance = weeklyRetrofitFactory_weather.getWeekly_weatherApi()
                .getList(API_KEY, "1", weatherCode, date+set_time, "JSON");//하루전꺼 조사해서 3,4,5,6이면 당일기준 2,3,4,5 시간수정
        getWeatherInstance.enqueue(weeklyWeatherCallback);

        Call<WeeklyModel_Temp> getTempInstance = weeklyRetrofitFactory_temp.getWeekly_tempApi()
                .getList(API_KEY, "1", tempCode, date + set_time, "JSON");
        getTempInstance.enqueue(weeklyTempCallback);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }




    private Callback<WeeklyModel_weather> weeklyWeatherCallback =new Callback<WeeklyModel_weather>() {
        @Override
        public void onResponse(Call<WeeklyModel_weather> call, Response<WeeklyModel_weather> response) {
            Log.d("My Tag", "response= "+response.raw().request().url().url());
            System.out.println("여기 지나가니?");

            Item item = response.body().getResponse().getBody().getItems().getItem()[0];
            System.out.println(item);

            day-=1;
            for(int i = 0; i<5; i++)
                setData(day,i,item);
        }

        @Override
        public void onFailure(Call<WeeklyModel_weather> call, Throwable t) {
            t.printStackTrace();
            Log.v("My Tag", "response= "+call.request().url());
            System.out.println("실패");

        }
    };

    private final Callback<WeeklyModel_Temp> weeklyTempCallback = new Callback<WeeklyModel_Temp>() {
        @Override
        public void onResponse(Call<WeeklyModel_Temp> call, Response<WeeklyModel_Temp> response) {
            Log.d("My Tag", "response= " + response.raw().request().url().url());

            com.example.main_w.weekly.temp_model.Item item = response.body().getResponse().getBody().getItems().getItem()[0];
            System.out.println(item);

            for (int i = 0; i < 5; i++) {
                adapter.setTempData(i, item.getTemp(i));
            }
        }

        @Override
        public void onFailure(Call<WeeklyModel_Temp> call, Throwable t) {
            t.printStackTrace();
            Log.v("My Tag", "response= " + call.request().url());
            System.out.println("실패");
        }
    };


    private void init(ViewGroup v){
        recyclerView=v.getRootView().findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WeeklyMainAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for(int i=0;i<5;i++)
        {
            data=new WeeklyDayWeatherData("월요일", weather,"-8/5", "20",R.drawable.sunny);
            adapter.addItem(data);
        }
    }

    private void setData(int day,int i, Item item) {
        adapter.setDayData(i, dayarr[(day+i+3)%7]);
        adapter.setRain_prob(i, item.getRain_prob(i));
        String weather = item.getWeather(i);
        if(weather.equals("맑음")) {
            adapter.setWeatherData(i,weather,R.drawable.sunny);
        }
        else if(weather.equals("구름많고 비")||weather.equals("구름많고 비/눈")||weather.equals("구름많고 소나기")||weather.equals("흐리고 비")||weather.equals("흐리고 비/눈")||weather.equals("흐리고 소나기")) {
            adapter.setWeatherData(i,"비", R.drawable.rain);
        }
        else if (weather.equals("구름많고 눈")||weather.equals("흐리고 눈")) {
            adapter.setWeatherData(i,"눈", R.drawable.snow);
        }
        else if(weather.equals("구름많음")||weather.equals("흐림")) {
            adapter.setWeatherData(i,"흐림", R.drawable.cloud);
        }
        else
        {
            adapter.setWeatherData(i,"error", R.drawable.cloud);
        }
    }


}