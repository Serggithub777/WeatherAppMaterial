package com.example.weatherappmaterial;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class RequesterApi {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/find?q=&units=metric&appid=";
    private static final String WEATHER_API_KEY = "1186203397d0695eb17fe6d368432737";
    private static final String TAG = "WEATHER";
    private StartFragment startFragment;
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
        HttpsURLConnection urlConnection = null;
        try {
            URL uri = new URL(request);
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            final String resultRequest = getLines(reader);
        }catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "Fail connection", e);
            e.printStackTrace();
        }finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
        return resultRequest;
    }

    private String getLines(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private String createRequestString(String enteredCityName) {
                StringBuffer stringBufferMetricRequest = new StringBuffer(WEATHER_URL + WEATHER_API_KEY);
                return stringBufferMetricRequest.insert(47, enteredCityName).toString();
            }
}
