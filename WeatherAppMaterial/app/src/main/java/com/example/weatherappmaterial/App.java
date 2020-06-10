package com.example.weatherappmaterial;

import android.app.Application;

import androidx.room.Room;

import com.example.weatherappmaterial.dao.WeatherDao;

public class App extends Application {
    private static App instance;
    private WeatherAppDatabase wad;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        wad = Room.databaseBuilder(
                getApplicationContext(),
                WeatherAppDatabase.class,
                "weather_app_database")
                .allowMainThreadQueries()
                .build();
    }

    public WeatherDao getWeatherDao() {
        return  wad.getWeatherDao();
    }
}
