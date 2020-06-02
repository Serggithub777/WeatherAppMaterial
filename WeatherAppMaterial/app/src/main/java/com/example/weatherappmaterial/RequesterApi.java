package com.example.weatherappmaterial;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class RequesterApi {
    private static final String WEATHER_URL = "https://api.openweathermap.org/";
    private static final String WEATHER_API_KEY = "1186203397d0695eb17fe6d368432737";
    private static final String TAG = "WEATHER";
    private SharedPreferences sharedPref;
    private StartFragmentDialog startFragmentDialog;

    public static OpenWeather getApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(WEATHER_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build();

        OpenWeather requesterApi = retrofit.create(OpenWeather.class);
        return requesterApi;

    }



}
