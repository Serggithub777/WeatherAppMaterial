package com.example.weatherappmaterial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherappmaterial.data.Weather;
import com.example.weatherappmaterial.data.WeatherRequest;
import com.google.gson.Gson;

import java.text.DecimalFormat;


public class WeatherFragment extends Fragment {
    private static final String TAG = "WEATHER";
    public static final String SHARED_PREF_ADD_SHOW = "show_additional_prefer";
    public static final String SETTINGS_SWITCH_PRESSURE = "switch_pressure";
    public static final String SETTINGS_SWITCH_HUMIDITY = "switch_humidity";
    public static final String SETTINGS_SWITCH_WIND = "switch_wind";

    public static final String SHARED_PREF_TEMP_MESUR = "temp_mesure_pref";
    public static final String TEMP_RADIOBUTTON_INDEX = "temp_radiobutton_index";

    private final static String m24 = "k:mm";
    private TextView textViewCityName;
    private TextView textViewTextWeatherIcon;
    private TextView textViewItemTemperature;
    private TextView textViewPlusTemp;
    private TextView textViewTempMesur;
    private TextView textViewPresureValue;
    private TextView textViewHumidityValue;
    private TextView textViewWindValue;
    private TextView textViewTime;
    SharedPreferences settingsShowAdditional;
    SharedPreferences settingsTempMesur;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View weatherFragment = inflater.inflate(R.layout.fragment_weather, container, false);
        settingsShowAdditional = requireContext().getSharedPreferences(SHARED_PREF_ADD_SHOW, Context.MODE_PRIVATE);

        loadAddShowPreferencesPressure(weatherFragment, settingsShowAdditional);
        loadAddShowPreferencesHumidity(weatherFragment, settingsShowAdditional);
        loadAddShowPreferencesWindSpeed(weatherFragment, settingsShowAdditional);

        return weatherFragment;
    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherRequest weatherRequest = DataHolder.getInstance().getResultRequest();
        dataSetting(view, weatherRequest);
        WeatherHistoryCreator weatherHistoryCreator = new WeatherHistoryCreator();
        weatherHistoryCreator.addNdrWeatherHistory();
    }

    private void dataSetting(@NonNull View view, WeatherRequest weatherRequest) {
        textViewCityName = view.findViewById(R.id.textViewCityName);
        setCityName(textViewCityName, weatherRequest);
        textViewTextWeatherIcon = view.findViewById(R.id.textViewTextWeatherIcon);
        setTextWeatherIcon(textViewTextWeatherIcon, weatherRequest);
        textViewPlusTemp = view.findViewById(R.id.textViewPlusTemp);
        textViewItemTemperature = view.findViewById(R.id.textViewItemTemperature);
        textViewTempMesur = view.findViewById(R.id.textViewTempMesur);
        setTemperature(textViewItemTemperature, weatherRequest);
        textViewPresureValue = view.findViewById(R.id.textViewPresureValue);
        setPressure(textViewPresureValue,weatherRequest);
        textViewHumidityValue = view.findViewById(R.id.textViewHumidityValue);
        setHumidity(textViewHumidityValue, weatherRequest);
        textViewWindValue = view.findViewById(R.id.textViewWindValue);
        setWindSpeed(textViewWindValue, weatherRequest);
    }

    private void setWindSpeed(TextView textViewWindValue, WeatherRequest weatherRequest) {
        float windValue = weatherRequest.getWind().getSpeed();
        String textWindValue = String.valueOf(windValue);
        textViewWindValue.setText(textWindValue);
    }

    private void setHumidity(TextView textViewHumidityValue, WeatherRequest weatherRequest) {
        int humidityValue = weatherRequest.getMain().getHumidity();
        String textHumidityValue = String.valueOf(humidityValue);
        textViewHumidityValue.setText(textHumidityValue);
    }

    private void setTemperature(TextView textViewItemTemperature, WeatherRequest weatherRequest) {
        float temperatureValue = 0;
        float temperatureValueCelsius = weatherRequest.getMain().getTemp();
        float temperatureValueFahrenheit = temperatureValueCelsius * 9 / 5 + 32;
        DecimalFormat df = new DecimalFormat("0.0");
        settingsTempMesur = requireContext().getSharedPreferences(SHARED_PREF_TEMP_MESUR,Context.MODE_PRIVATE);
        int saveRadioButtonIndex = settingsTempMesur.getInt(TEMP_RADIOBUTTON_INDEX, 0);
        if (saveRadioButtonIndex == 1) {
            temperatureValue = temperatureValueFahrenheit;
            textViewTempMesur.setText("F");
        } else temperatureValue = temperatureValueCelsius;

        if (temperatureValue > 0) {
            textViewItemTemperature.setText(df.format(temperatureValue));
        } else {
            textViewPlusTemp.setVisibility(View.INVISIBLE);
           textViewItemTemperature.setText(df.format(temperatureValue));
        }
    }

    private void setTextWeatherIcon(TextView textViewTextWeatherIcon, WeatherRequest weatherRequest) {
        Weather weather = weatherRequest.getWeather().get(0);
        textViewTextWeatherIcon.setText(weather.getDescription());
    }


    private void setPressure(TextView textViewPresureValue, WeatherRequest weatherRequest) {
        int pressureValue = weatherRequest.getMain().getPressure();
        textViewPresureValue.setText(String.valueOf(pressureValue));

    }

    private void setCityName(TextView textViewCityName, WeatherRequest weatherRequest) {
        textViewCityName.setText(weatherRequest.getName());
    }

    private void loadAddShowPreferencesPressure(View weatherFragment, SharedPreferences settingsShowAdditional) {
        TextView textViewAirPressure = weatherFragment.findViewById(R.id.textViewAirPressure);
        TextView textViewPresureValue = weatherFragment.findViewById(R.id.textViewPresureValue);
        TextView textViewPressureMesur = weatherFragment.findViewById(R.id.textViewPressureMesur);

        boolean airPressureSwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_PRESSURE, false);
        if (airPressureSwitch) {
            textViewAirPressure.setVisibility(View.VISIBLE);
            textViewPresureValue.setVisibility(View.VISIBLE);
            textViewPressureMesur.setVisibility(View.VISIBLE);
        }
    }

    private void loadAddShowPreferencesHumidity(View weatherFragment, SharedPreferences settingsShowAdditional) {
        TextView textViewHumidity = weatherFragment.findViewById(R.id.textViewHumidity);
        TextView textViewHumidityValue = weatherFragment.findViewById(R.id.textViewHumidityValue);
        TextView textViewHumidityMesur = weatherFragment.findViewById(R.id.textViewHumidityMesur);

        boolean humiditySwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_HUMIDITY, false);
        if (humiditySwitch) {
            textViewHumidity.setVisibility(View.VISIBLE);
            textViewHumidityValue.setVisibility(View.VISIBLE);
            textViewHumidityMesur.setVisibility(View.VISIBLE);
        }
   }

    private void loadAddShowPreferencesWindSpeed(View weatherFragment, SharedPreferences settingsShowAdditional) {
        TextView textViewWindSpeed = weatherFragment.findViewById(R.id.textViewWindSpeed);
        TextView textViewWindValue = weatherFragment.findViewById(R.id.textViewWindValue);
        TextView textViewWindSpeedMesur = weatherFragment.findViewById(R.id.textViewWindSpeedMesur);

        boolean windSpeedSwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_WIND, false);
        if (windSpeedSwitch) {
            textViewWindSpeed.setVisibility(View.VISIBLE);
            textViewWindValue.setVisibility(View.VISIBLE);
            textViewWindSpeedMesur.setVisibility(View.VISIBLE);
        }
    }

    private void setWeatherIcon() {

    }

    public void callParentMethod(){
        requireActivity().onBackPressed();
    }
}
