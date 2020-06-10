package com.example.weatherappmaterial;

import android.annotation.SuppressLint;

import com.example.weatherappmaterial.dao.WeatherDao;
import com.example.weatherappmaterial.data.WeatherRequest;
import com.example.weatherappmaterial.model.WeatherHistory;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class WeatherHistoryCreator {


    private WeatherRequest weatherRequest = DataHolder.getInstance().getResultRequest();
    private WeatherDao weatherDao = App.getInstance().getWeatherDao();
    private WeatherSource weatherSource = new WeatherSource(weatherDao);

    public void addNdrWeatherHistory() {
        weatherSource.addWeatherHistory(ndrWeatherHistory());
    }

    private WeatherHistory ndrWeatherHistory() {
        WeatherHistory weatherHistory = new WeatherHistory();
        return ndrUpdatedWeatherHistory(weatherHistory);
    }

    private WeatherHistory ndrUpdatedWeatherHistory(WeatherHistory weatherHistory) {
        weatherHistory.cityName = getCityNameFromRequest();
        weatherHistory.temp = getTempFromRequest();
        weatherHistory.date = getDate();

        return weatherHistory;
    }

    private String getCityNameFromRequest() {
        return weatherRequest.getName();
    }

    private String getTempFromRequest() {
        return String.valueOf(weatherRequest.getMain().getTemp());
    }

    private String getDate() {
        @SuppressLint("SimpleDateFormat")
        String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return dateStr;
    }
}
