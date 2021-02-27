package com.example.main_w.weather_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("serial")
@Entity(tableName = "alarmTable")
public class Alarm implements Serializable{
    
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int hour, minute;

    // rain == 1
    // snow == 2
    // typhoon ==3
    @NonNull
    private int weather;

    private String name, repeat, time;
    private boolean isEnabled;
    private List<Boolean> days;     // sun == 0

    @Ignore
    public Alarm(){}

    public Alarm(int hour, int minute, String name, String repeat, String time, 
                boolean isEnabled, List<Boolean> days, int weather){
        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.repeat = repeat;
        this.time = time;
        this.isEnabled = isEnabled;
        this.days = days;
        this.weather = weather;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getHour(){
        return hour;
    }

    public void setHour(int hour){
        this.hour = hour;
    }

    public int getMinute(){
        return minute;
    }

    public void setMinute(int minute){
        this.minute = minute;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRepeat(){
        return repeat;
    }

    public void setRepeat(String repeat){
        this.repeat = repeat;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public List<Boolean> getDays(){
        return days;
    }

    public void setDays(List<Boolean> days){
        this.days = days;
    }

    public int getWeather(){
        return weather;
    }

    public void setWeather(int weather){
        this.weather = weather;
    }

    public boolean getIsEnabled(){
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }

    public void setAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("alarm", this);
        intent.putExtra("bundle", bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if(calendar.before(Calendar.getInstance()))
            calendar.add(Calendar.DATE, 1);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        else
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
