package com.example.main_w.weekly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.R;

import java.util.ArrayList;

public class WeeklyMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WeeklyDayWeatherData> listData = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_recycler_item, parent, false);
        return new ViewHolderDay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderDay)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(WeeklyDayWeatherData data){
        listData.add(data);
    }

    void setTempData(int index, String temp)
    {
        WeeklyDayWeatherData data=listData.get(index);
        data.setTemp_text(temp);
        listData.set(index, data);
    }

    void setWeatherData(int index, String weather_text, int weather_img)
    {
        WeeklyDayWeatherData data=listData.get(index);
        data.setWeather_img(weather_img);
        data.setWeather_text(weather_text);
        listData.set(index, data);
    }

    void setDayData(int index, String day)
    {
        WeeklyDayWeatherData data=listData.get(index);
        data.setDay_text(day);
        listData.set(index, data);
    }

    void setRain_prob(int index, String rain_prob)
    {
        WeeklyDayWeatherData data=listData.get(index);
        data.setRain_prob_text(rain_prob);
        listData.set(index, data);
    }

}


