package com.example.main_w.location;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locationTable")
public class Location {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "code")
    public String code;

    @NonNull
    @ColumnInfo(name = "axisX")
    public int axisX;

    @NonNull
    @ColumnInfo(name = "axisY")
    public int axisY;
}
