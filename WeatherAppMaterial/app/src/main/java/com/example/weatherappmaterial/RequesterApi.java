package com.example.weatherappmaterial;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

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
