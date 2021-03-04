package com.example.main_w;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "weather_alarm";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel();
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "날씨 알람", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("날씨에 따라 알람이 울립니다");
            channel.enableVibration(true);
            channel.enableLights(false);
            channel.setShowBadge(false);

            AudioAttributes audio = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audio);

            NotificationManager notiManager = getSystemService(NotificationManager.class);
            notiManager.createNotificationChannel(channel);
        }
    }
}
