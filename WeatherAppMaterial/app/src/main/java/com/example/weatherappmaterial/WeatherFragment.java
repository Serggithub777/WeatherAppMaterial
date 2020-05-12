package com.example.weatherappmaterial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.weatherappmaterial.data.WeatherRequest;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.logging.Handler;

public class WeatherFragment extends Fragment {
      private TextView textViewCityName;
      String nameCity;

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
        textViewCityName = view.findViewById(R.id.textViewCityName);
        setCityName(textViewCityName);


    }

    private void setCityName(TextView textViewCityName) {
        if (getArguments() != null) {
           String resultRequest = getArguments().getString("ResultRequest");
           Gson gson = new Gson();
           final WeatherRequest weatherRequest = gson.fromJson(resultRequest, WeatherRequest.class);
           String nameCity = weatherRequest.getName();
           Toast.makeText(getContext(), nameCity, Toast.LENGTH_SHORT).show();
           textViewCityName.setText(weatherRequest.getName());
            }

        }

    public void callParentMethod(){
        requireActivity().onBackPressed();
    }

//  NavHostFragment.findNavController(WeatherFragment.this).navigate(R.id.action_weatherFragment_to_startFragment);

}
