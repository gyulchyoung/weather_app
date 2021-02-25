package com.example.main_w.weather_alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.main_w.weather_alarm.Converter;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AlarmDatabase extends RoomDatabase {
    private static volatile AlarmDatabase ALARM_INSTANCE;
    public static final String DB_NAME = "alarms";

    public abstract AlarmDao alarmDao();

    public static AlarmDatabase getDatabases(final Context context){
        
        if(ALARM_INSTANCE == null){
            synchronized (AlarmDatabase.class){
                if(ALARM_INSTANCE == null){
                    ALARM_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AlarmDatabase.class, DB_NAME)
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build();
                }
            }
        }
        return ALARM_INSTANCE;
    }

    public static void destroyInstance(){
        ALARM_INSTANCE = null;
    }
}
