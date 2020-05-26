package com.example.weatherappmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.weatherappmaterial.R;
import com.example.weatherappmaterial.data.WeatherRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class StartFragment extends Fragment {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/find?q=&units=metric&appid=";
    private static final String WEATHER_API_KEY = "1186203397d0695eb17fe6d368432737";
    TextInputEditText enteredCityName;
    private Button buttonShowWeather;
    private View.OnClickListener clickListenerShowWeather;
    private TextInputEditText editText;
    private final String failResponse = "{\"message\":\"accurate\",\"cod\":\"200\",\"count\":0,\"list\":[]}";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View activityFragmentStart = inflater.inflate(R.layout.fragment_start, container, false);
        enteredCityName = activityFragmentStart.findViewById(R.id.textInputCityEnter);
        buttonShowWeather = activityFragmentStart.findViewById(R.id.buttonShowWeather);
        onClickListenerShowWeather();
        buttonShowWeather.setOnClickListener(clickListenerShowWeather);
        return activityFragmentStart;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout viewRecentCitiesList = view.findViewById(R.id.recent_cities_List_linear_layout);
        initListRecentCities(viewRecentCitiesList);

    }

    private void onClickListenerShowWeather() {
        clickListenerShowWeather = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String cityName = enteredCityName.getText().toString().trim();
                try {
                if (cityName.isEmpty()) {
                    createToast("Enter city name!");
                } else {
                        String request = createRequestString(cityName);
                        final URL uri = new URL(request);
                        final Handler handler = new Handler();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpsURLConnection urlConnection = null;
                                try {
                                    urlConnection = (HttpsURLConnection) uri.openConnection();
                                    urlConnection.setRequestMethod("GET");
                                    urlConnection.setReadTimeout(10000);
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                                    final String resultRequest = getLines(reader);
                                    //validateResponse(resultRequest);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (resultRequest.equals(failResponse)) {
                                                createToast("Wrong City Name");
                                            } else {
                                                Bundle args = new Bundle();
                                                args.putString("ResultRequest", resultRequest);
                                                Navigation.findNavController(view).navigate(R.id.action_startFragment_to_weatherFragment, args);
                                            }
                                        }
                                     });
                                } catch (Exception e) {
                                    Log.e(TAG, "Fail connection", e);
                                    e.printStackTrace();
                                } finally {
                                    if (null != urlConnection) {
                                        urlConnection.disconnect();
                                    }
                                }
                            }
                        }).start();
                    }
                    }catch (MalformedURLException e) {
                    Log.e(TAG, "Fail URI", e);
                    e.printStackTrace();
                }
            }


            private String getLines(BufferedReader reader) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        };
    }

    private void initListRecentCities(LinearLayout viewRecentCitiesLinearLayout) {
        String[] recentCities = getResources().getStringArray(R.array.recentCities);
        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < recentCities.length; i++) {
            final String city = recentCities[i];
            View viewRecentCityItem = layoutInflater.inflate(R.layout.recent_city_list_item,
                    viewRecentCitiesLinearLayout, false);
            TextView textView = viewRecentCityItem.findViewById(R.id.textViewRecentCityItem);
            textView.setText(city);
            viewRecentCitiesLinearLayout.addView(viewRecentCityItem);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextInputEditText cityName = requireView().
                            findViewById(R.id.textInputCityEnter);
                    cityName.setText(city);
                }
            });
        }
    }
    private String createRequestString(String enteredCityName) {
            StringBuffer stringBufferMetricRequest = new StringBuffer(WEATHER_URL + WEATHER_API_KEY);
            return stringBufferMetricRequest.insert(47, enteredCityName).toString();
        }
    private void validateResponse(String resultRequest) {
        if (resultRequest.equals(failResponse)) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Message", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void createToast(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    }



