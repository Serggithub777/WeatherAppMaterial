package com.example.weatherappmaterial;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;


public class RequesterApi {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/find?q=&units=metric&appid=";
    private static final String WEATHER_API_KEY = "1186203397d0695eb17fe6d368432737";
    private static final String TAG = "WEATHER";
    private String resultRequest = null;
    private final RequesterApiListener requesterApiListener;

    public interface RequesterApiListener {
        void onFinish(String result);
    }

    public RequesterApi(RequesterApiListener requesterApiListener) {
        this.requesterApiListener = requesterApiListener;
    }

    public void setRequesterApiListener(final String cityName) {
        final Handler handler  = new Handler(Looper.myLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = doApiRequest(cityName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requesterApiListener.onFinish(result);
                    }
                });
            }
        }).start();
    }


    public String doApiRequest(String cityName) {
        String request = createRequestString(cityName);

        try{
            final URL uri = new URL(request);
                    HttpURLConnection urlConnection;
                    try {
                        urlConnection = (HttpURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        resultRequest = getLines(in);

                    } catch (Exception e) {
                        Log.e(TAG,"Fail Connection ", e);
                        e.printStackTrace();
                    }

        } catch (Exception e) {
            Log.e(TAG,"Fail URL ", e);
            e.printStackTrace();
        }

        return  resultRequest;
    }
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
    private String createRequestString(String enteredCityName) {
                StringBuffer stringBufferMetricRequest = new StringBuffer(WEATHER_URL + WEATHER_API_KEY);
                return stringBufferMetricRequest.insert(47, enteredCityName).toString();
            }
}
