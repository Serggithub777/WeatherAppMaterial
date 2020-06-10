package com.example.weatherappmaterial;

import android.app.Application;

import androidx.room.Room;

import com.example.weatherappmaterial.dao.WeatherDao;
import com.example.weatherappmaterial.model.WeatherHistory;

import java.util.List;

public class WeatherSource {
    private final WeatherDao weatherDao;
    private List<WeatherHistory> weatherHistories;

    public WeatherSource(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public List <WeatherHistory> getWeatherHistories() {
        if (weatherHistories == null) {
            loadWeatherHistories();
        }
        return weatherHistories;
    }

    private void loadWeatherHistories() {
        weatherHistories = weatherDao.getAllWeatherHistory();
    }

    public long getCountWeatherHistories() {
        return weatherDao.getCountWeatherHistory();
    }

    public void addWeatherHistory(WeatherHistory weatherHistory) {
        weatherDao.insertWeatherHistory(weatherHistory);
    }

    public void removeWeatherHistory(long id) {
        weatherDao.deleteWeatherHistoryById(id);
    }

}
