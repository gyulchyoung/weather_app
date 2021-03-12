package com.example.Magic_CnyangE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.Magic_CnyangE.weather_alarm.Alarm;
import com.example.Magic_CnyangE.weather_alarm.AlarmDatabase;

import java.util.List;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                || intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)) {
            AlarmDatabase alarmDB = AlarmDatabase.getDatabases(context);

            new Thread(() -> {
                List<Alarm> alarmList = alarmDB.alarmDao().getAllList();
                for(Alarm a : alarmList)
                    if (a.getIsEnabled())
                        a.setAlarm(context);
            }).start();
        }
    }
}
