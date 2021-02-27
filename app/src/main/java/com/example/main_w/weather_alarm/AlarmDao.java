package com.example.main_w.weather_alarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);
    
    @Query("SELECT * FROM alarmTable")
    List<Alarm> getAllList();

    @Query("SELECT * FROM alarmTable")
    LiveData<List<Alarm>> getAll();

    @Query("DELETE FROM alarmTable")
    void deleteAll();
}
