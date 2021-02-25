package com.example.main_w.location;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM locationTable WHERE country LIKE :search")
    List<Location> findLocationsWithCountry(String search);
}
