package com.example.weatherappmaterial.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"date", "city_name", "temperature"})})
public class WeatherHistory {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "city_name")
    public String cityName;

    @ColumnInfo(name = "temperature")
    public String temp;
}