package com.example.weatherappmaterial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherappmaterial.data.Weather;
import com.example.weatherappmaterial.data.WeatherRequest;
import com.example.weatherappmaterial.data.WeatherRequestList;
import com.google.gson.Gson;


public class WeatherFragment extends Fragment {
      private final static String m24 = "k:mm";
      private TextView textViewCityName;
      private TextView textViewTextWeatherIcon;
      private TextView textViewItemTemperature;
      private TextView textViewPlusTemp;
      private TextView textViewPresureValue;
      private TextView textViewHumidityValue;
      private TextView textViewWindValue;
      private TextView textViewTime;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String resultRequest = getArguments().getString("ResultRequest");
            Gson gson = new Gson();
            final WeatherRequestList weatherRequestList = gson.fromJson(resultRequest, WeatherRequestList.class);
            WeatherRequest [] weatherRequest = weatherRequestList.getList();
            dataSetting(view, weatherRequest);
        }
}

    private void dataSetting(@NonNull View view, WeatherRequest[] weatherRequest) {
        textViewCityName = view.findViewById(R.id.textViewCityName);
        setCityName(textViewCityName, weatherRequest);
        textViewTextWeatherIcon = view.findViewById(R.id.textViewTextWeatherIcon);
        setTextWeatherIcon(textViewTextWeatherIcon, weatherRequest);
        textViewPlusTemp = view.findViewById(R.id.textViewPlusTemp);
        textViewItemTemperature = view.findViewById(R.id.textViewItemTemperature);
        setTemperature(textViewItemTemperature, weatherRequest);
        textViewPresureValue = view.findViewById(R.id.textViewPresureValue);
        setPressure(textViewPresureValue,weatherRequest);
        textViewHumidityValue = view.findViewById(R.id.textViewHumidityValue);
        setHumidity(textViewHumidityValue, weatherRequest);
        textViewWindValue = view.findViewById(R.id.textViewWindValue);
        setWindSpeed(textViewWindValue, weatherRequest);
    }

    private void setWindSpeed(TextView textViewWindValue, WeatherRequest[] weatherRequest) {
        float windValue = weatherRequest[0].getWind().getSpeed();
        String textWindValue = String.valueOf(windValue);
        textViewWindValue.setText(textWindValue);
   }

    private void setHumidity(TextView textViewHumidityValue, WeatherRequest[] weatherRequest) {
        int humidityValue = weatherRequest[0].getMain().getHumidity();
        String textHumidityValue = String.valueOf(humidityValue);
        textViewHumidityValue.setText(textHumidityValue);
    }

    private void setTemperature(TextView textViewItemTemperature, WeatherRequest[] weatherRequest) {
        float temperatureValue = weatherRequest[0].getMain().getTemp();
        String textTemperatureValue = String.valueOf(temperatureValue);
        if (temperatureValue > 0) {
            textViewItemTemperature.setText(textTemperatureValue);
        } else {
            textViewPlusTemp.setVisibility(View.INVISIBLE);
            textViewItemTemperature.setText(textTemperatureValue);
        }
    }

    private void setTextWeatherIcon(TextView textViewTextWeatherIcon, WeatherRequest[] weatherRequest) {
        Weather[] weather = weatherRequest[0].getWeatherList();
        String textWeatherIcon = weather[0].getDescription();
        textViewTextWeatherIcon.setText(textWeatherIcon);
    }


    private void setPressure(TextView textViewPresureValue, WeatherRequest[] weatherRequest) {
        int pressureValue = weatherRequest[0].getMain().getPressure();
        textViewPresureValue.setText(String.valueOf(pressureValue));

    }

    private void setCityName(TextView textViewCityName, WeatherRequest[] weatherRequest) {
        textViewCityName.setText(weatherRequest[0].getName());
    }

    private void setWeatherIcon() {

    }

    public void callParentMethod(){
        requireActivity().onBackPressed();
    }
}
