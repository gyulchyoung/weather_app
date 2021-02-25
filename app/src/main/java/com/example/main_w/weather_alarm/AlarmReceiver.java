package com.example.main_w.weather_alarm;

import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;

import com.example.main_w.R;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_TAG = "alert_weather_alarm";

    private String alertStr;

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getBundleExtra("bundle");
        Alarm alertAlarm = (Alarm) bundle.getSerializable("alarm");
        AlarmDatabase alarmDB = AlarmListActivity.alarmDB;

        setAlertStr(context, alertAlarm.getWeather());

        PowerManager pManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "weatheralarm:WAKELOCK");

        wakeLock.acquire();

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = buildNotification(context, alertStr);

        String isRepeat = alertAlarm.getRepeat();
        if(isRepeat == null || isRepeat.isEmpty()){
            nManager.notify(NOTIFICATION_TAG, 0, notification);
            alertAlarm.setIsEnabled(false);
            new Thread(() -> {
                alarmDB.alarmDao().update(alertAlarm);
            }).start();
        }
        else{
            if(isAlarmToday(alertAlarm.getDays()))
                nManager.notify(NOTIFICATION_TAG, 0, notification);
            alertAlarm.setAlarm(context);
        }

        wakeLock.release();
    }

    public Notification buildNotification(Context context, String alertStr){
        Notification notification;
        Intent mIntent = new Intent(context, AlarmAlertActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("alertStr", alertStr);
        mIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification.Builder nBuilder = new Notification.Builder(context, "weather_alarm");
            nBuilder.setSmallIcon(R.drawable.ic_stat_alarm)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(alertStr)
                .setCategory(Notification.CATEGORY_ALARM)
                .setFullScreenIntent(pendingIntent, true)
                .setTimeoutAfter(60 * 1000)
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PUBLIC);
            notification = nBuilder.build();
        }
        else{
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, "weather_alarm");
            nBuilder.setSmallIcon(R.drawable.ic_stat_alarm)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(alertStr)
                .setCategory(Notification.CATEGORY_ALARM)
                .setFullScreenIntent(pendingIntent, true)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notification = nBuilder.build();
        }

        return notification;
    }

    public boolean isAlarmToday(List<Boolean> isDay){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 1(sun) ~ 7(sat)

        if(isDay.get(dayOfWeek - 1))
            return true;
        else
            return false;
    }

    public void setAlertStr(Context context, int weatherType){
        switch (weatherType){
            case 1:
                alertStr = context.getString(R.string.alert_rain);
                break;
            case 2:
                alertStr = context.getString(R.string.alert_snow);
                break;
            case 3:
                alertStr = context.getString(R.string.alert_typhoon);
                break;
        }
    }
}
