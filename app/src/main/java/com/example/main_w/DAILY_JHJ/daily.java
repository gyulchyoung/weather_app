package com.example.main_w.DAILY_JHJ;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main_w.R;

public class daily extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.daily, container, false);
        View view = inflater.inflate(R.layout.weather_item, container, false);

        TextView timeView = view.findViewById(R.id.time_view);
        TextView temperatureView = view.findViewById(R.id.temparature_view);
        TextView rainrateView = view.findViewById(R.id.rain_rate_view);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        WeatherAdapter adapter = new WeatherAdapter();
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 24; i++) {
            adapter.addItem(new weather_info("15도", i+"시", "60%"));
        }

        return v;
    }

}
