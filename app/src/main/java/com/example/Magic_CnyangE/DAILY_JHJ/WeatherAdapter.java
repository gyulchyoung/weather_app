package com.example.Magic_CnyangE.DAILY_JHJ;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Magic_CnyangE.R;

import java.util.ArrayList;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
    ArrayList<weather_info> items = new ArrayList<weather_info>();

    public void addItem(String tem, String time, String rain, int icon){
        weather_info item = new weather_info();

        item.setTemperature(tem);
        item.setTime(time);
        item.setRain_rate(rain);
        item.setImg(icon);

        items.add(item);
    }

    public void setItems(ArrayList<weather_info> items){
        this.items = items;
    }

    public weather_info getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, weather_info item){
        items.set(position, item);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.weather_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        weather_info item= items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time_view;
        TextView temparature_view;
        TextView rain_rate_view;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            temparature_view = itemView.findViewById(R.id.temparature_view);
            time_view = itemView.findViewById(R.id.time_view);
            rain_rate_view = itemView.findViewById(R.id.rain_rate_view);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setItem(weather_info item){
            time_view.setText(item.getTime());
            temparature_view.setText(item.getTemperature());
            rain_rate_view.setText(item.getRain_rate());
            imageView.setImageResource(item.getImg());
        }
    }
}
