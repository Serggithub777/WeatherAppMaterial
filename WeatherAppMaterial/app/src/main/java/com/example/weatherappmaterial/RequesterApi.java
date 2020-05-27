package com.example.weatherappmaterial;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
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

    public String doApiRequest(String cityName) {
        String request = createRequestString(cityName);

        try {
            final URL uri = new URL(request);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        resultRequest = getLines(reader);

                        } catch (IOException e) {
                        e.printStackTrace();

                        } finally {
                           if (null != urlConnection) {
                            urlConnection.disconnect();

                        }
                    }
                }
                private String getLines(BufferedReader reader) {
                    return reader.lines().collect(Collectors.joining("\n"));
                }

              }).start();
            }catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
        return resultRequest;
    }

            private String createRequestString(String enteredCityName) {
                StringBuffer stringBufferMetricRequest = new StringBuffer(WEATHER_URL + WEATHER_API_KEY);
                return stringBufferMetricRequest.insert(47, enteredCityName).toString();
            }
}
