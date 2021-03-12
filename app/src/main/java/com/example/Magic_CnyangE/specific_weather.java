package com.example.Magic_CnyangE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.Magic_CnyangE.DAILY_JHJ.daily;
import com.example.Magic_CnyangE.weekly.WeeklyFragment;

public class specific_weather extends AppCompatActivity {

    private WeeklyFragment weeklyFragment;
    private daily dailyFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_weather);

        FragmentManager manager = getSupportFragmentManager();
        dailyFragment = (daily) manager.findFragmentById(R.id.fragment);
        weeklyFragment = (WeeklyFragment) manager.findFragmentById(R.id.weekly_weather);

    }
}
