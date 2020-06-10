package com.example.weatherappmaterial;
import android.os.Bundle;
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

import com.example.weatherappmaterial.data.WeatherRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartFragment extends Fragment {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/find?q=&units=metric&appid=";
    private static final String WEATHER_API_KEY = "1186203397d0695eb17fe6d368432737";
    TextInputEditText enteredCityName;
    private Button buttonShowWeather;
    private View.OnClickListener clickListenerShowWeather;
    private TextInputEditText editText;
    private OpenWeather openWeather;
    private  StartFragmentDialog startFragmentDialog;
    private WeatherRequest resultRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View activityFragmentStart = inflater.inflate(R.layout.fragment_start, container, false);
        enteredCityName = activityFragmentStart.findViewById(R.id.textInputCityEnter);
        buttonShowWeather = activityFragmentStart.findViewById(R.id.buttonShowWeather);
        onClickListenerShowWeather();
        buttonShowWeather.setOnClickListener(clickListenerShowWeather);
        openWeather = RequesterApi.getApi();
        return activityFragmentStart;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout viewRecentCitiesList = view.findViewById(R.id.recent_cities_List_linear_layout);
        initListRecentCities(viewRecentCitiesList);

    }

    private void onClickListenerShowWeather() {
        clickListenerShowWeather = view -> {
            String cityName = enteredCityName.getText().toString().trim();

            if (cityName.isEmpty()) {
                createToast("Enter city name!");
            } else {
                openWeather.loadWeather(cityName, "metric", WEATHER_API_KEY).enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherRequest resultRequest = response.body();
                            DataHolder.getInstance().setResultRequest(resultRequest);
                            Navigation.findNavController(view).navigate(R.id.action_startFragment_to_weatherFragment);
                        }
                        if (!response.isSuccessful()&& response.errorBody()!=null) {
                            try {
                                JSONObject jsonError = new JSONObject(response.errorBody().string());
                                String error = jsonError.getString("message");
                                Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                                startFragmentDialog = new StartFragmentDialog();
                                startFragmentDialog.show(getParentFragmentManager(), "startFragmentDialog");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t){

                    }

                });
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

    public void createToast(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}



