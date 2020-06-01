package com.example.weatherappmaterial.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherRequestList {

    @SerializedName("list")
    @Expose
    WeatherRequest [] list;

    public WeatherRequest[] getList() {
        return list;
    }

    public void setList(WeatherRequest[] list) {
        this.list = list;
    }


}
