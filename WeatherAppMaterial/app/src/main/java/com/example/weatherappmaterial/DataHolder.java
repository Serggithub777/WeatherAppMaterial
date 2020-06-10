package com.example.weatherappmaterial;

import com.example.weatherappmaterial.data.WeatherRequest;

public class DataHolder {
    private static DataHolder dataObject = null;

    private WeatherRequest resultRequest;

    private DataHolder() {

    }

    public static DataHolder getInstance() {
        if (dataObject == null) {
            dataObject = new DataHolder();
        } return dataObject;
    }
    public WeatherRequest getResultRequest() {
        return resultRequest;
    }

    public void setResultRequest(WeatherRequest resultRequest) {
        this.resultRequest = resultRequest;
    }

}
