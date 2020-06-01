package com.example.weatherappmaterial;
import com.example.weatherappmaterial.data.WeatherRequest;
import com.example.weatherappmaterial.data.WeatherRequestList;
import com.google.gson.Gson;

class ApiDataHandler {
    private String resultRequest;

    ApiDataHandler( String resultRequest) {
        this.resultRequest = resultRequest;
    }

   WeatherRequest [] getWeatherRequests() {
        Gson gson = new Gson();
        final WeatherRequestList weatherRequestList = gson.fromJson(resultRequest, WeatherRequestList.class);
        return weatherRequestList.getList();
    }
}
