package com.example.main_w;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.main_w.weather_alarm.Alarm;
import com.example.main_w.weather_alarm.AlarmDatabase;
import com.example.main_w.weather_alarm.AlarmListActivity;

import java.util.List;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmDatabase alarmDB = AlarmListActivity.alarmDB;

            new Thread(() -> {
                List<Alarm> alarmList = alarmDB.alarmDao().getAllList();
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(Alarm a : alarmList) {
                            if (a.getIsEnabled())
                                a.setAlarm(context);
                        }
                    }
                });
            }).start();
        }
    }
}
