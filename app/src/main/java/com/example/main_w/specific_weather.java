package com.example.main_w;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.DAILY_JHJ.daily;
import com.example.main_w.weekly.WeeklyFragment;

public class specific_weather extends AppCompatActivity {

    private WeeklyFragment weeklyFragment;
    private daily dailyFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_weather);

//        FragmentManager manager = getSupportFragmentManager();
//        dailyFragment = (daily) manager.findFragmentById(R.id.fragment);
//        weeklyFragment = (WeeklyFragment) manager.findFragmentById(R.id.weekly_weather);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String locationX = data.getStringExtra("locationX");
        String locationY = data.getStringExtra("locationY");

        Bundle bundle = new Bundle();
        bundle.putString("locationX",locationX);
        bundle.putString("locationY",locationY);

        daily dailyFragment = new daily();
        dailyFragment.setArguments(bundle);

    }
}
