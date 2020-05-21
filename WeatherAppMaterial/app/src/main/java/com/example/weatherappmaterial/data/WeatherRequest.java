package com.example.weatherappmaterial.data;

public class WeatherRequest {
    private Coord coord;
    private Weather [] weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String name;

    public Weather[] getWeatherList() {
        return weather;
    }

    public void setWeatherList(Weather[] weather) {
        this.weather = weather;
    }



    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

