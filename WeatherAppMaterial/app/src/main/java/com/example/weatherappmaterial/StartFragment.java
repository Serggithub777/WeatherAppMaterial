package com.example.weatherappmaterial;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
    private RequesterApi requesterApi;
    private  StartFragmentDialog startFragmentDialog;

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

                RequesterApi requesterApi = new RequesterApi(new RequesterApi.RequesterApiListener() {
                    @Override
                    public void onFinish(String result) {
                        //Use result
                        if (result.equals(failResponse)) {
                            startFragmentDialog = new StartFragmentDialog();
                            startFragmentDialog.show(getParentFragmentManager(),"startFragmentDialog");
                        }
                        else {
                            Bundle args = new Bundle();
                            args.putString("ResultRequest", result);
                            Navigation.findNavController(view).navigate(R.id.action_startFragment_to_weatherFragment, args);
                        }
                    }
                });
                requesterApi.setRequesterApiListener(cityName);
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

    public void createToast(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    }



