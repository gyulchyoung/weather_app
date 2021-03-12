package com.example.Magic_CnyangE.weekly;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Magic_CnyangE.R;

public class ViewHolderDay extends RecyclerView.ViewHolder {

    TextView day_textView;
    ImageView weather_imgView;
    TextView weather_textView;
    TextView temp_textView;
    TextView rain_prob_textView;

    public ViewHolderDay(@NonNull View itemView) {
        super(itemView);

        day_textView = itemView.findViewById(R.id.day_textView);
        weather_imgView = itemView.findViewById(R.id.weather_imageView);
        weather_textView=itemView.findViewById(R.id.weather_textView);
        temp_textView = itemView.findViewById(R.id.temp_textView);
        rain_prob_textView = itemView.findViewById(R.id.rain_prob_textView);
    }

    public void onBind(WeeklyDayWeatherData data){
        day_textView.setText(data.getDay_text());
        weather_imgView.setImageResource(data.getWeather_img());
        weather_textView.setText(data.getWeather_text());
        temp_textView.setText(data.getTemp_text());
        rain_prob_textView.setText(data.getRain_prob_text());
    }
}
