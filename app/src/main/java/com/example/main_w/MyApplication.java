package com.example.main_w;

import android.app.Application;

import com.example.main_w.location.LocationDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        //알람 db 생성 (아직 적용 안 함)
        //AlarmListActivity.alarmDB = AlarmDatabase.getDatabases(this);

        //애플리케이션 실행 시 location.db 생성
        CityDialogFragment.locationDB = LocationDatabase.getDatabases(this);

    }
}
