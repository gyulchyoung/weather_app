package com.example.Magic_CnyangE.location;

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

    @ColumnInfo(name = "country_code")
    public String countryCode;

    @ColumnInfo(name = "city_code")
    public String cityCode;

    @NonNull
    @ColumnInfo(name = "axisX")
    public int axisX;

    @NonNull
    @ColumnInfo(name = "axisY")
    public int axisY;
}
