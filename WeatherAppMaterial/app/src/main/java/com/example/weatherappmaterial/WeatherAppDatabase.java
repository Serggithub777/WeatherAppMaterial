package com.example.weatherappmaterial;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weatherappmaterial.dao.WeatherDao;
import com.example.weatherappmaterial.model.WeatherHistory;

@Database(entities = {WeatherHistory.class}, version = 1)

public abstract class WeatherAppDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();

}

