package com.example.weatherappmaterial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final String TAG = "WEATHER";
    public static final String SETTINGS_SWITCH_PRESSURE = "switch_pressure";
    public static final String SETTINGS_SWITCH_HUMIDITY = "switch_humidity";
    public static final String SETTINGS_SWITCH_WIND = "switch_wind";
    public static final String SHARED_PREF_ADD_SHOW = "show_additional_prefer";
    // Required empty public constructor
    SharedPreferences settingsShowAdditional;
    Switch switchShowAirPressure;
    Switch switchShowHumidity;
    Switch switchShowWindSpeed;


    public SettingsFragment() {
    }


    @SuppressLint("Assert")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        //initSwitchesListeners(settingsShowAdditional);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences settingsShowAdditional = requireContext().getSharedPreferences(SHARED_PREF_ADD_SHOW, MODE_PRIVATE );
        if (settingsShowAdditional!=null){
            loadSwitchesPreferences(view, settingsShowAdditional);
        }
        initializeSwitches(view);
        initSwitchListeners();

    }

    private void loadSwitchesPreferences(@NonNull View view, SharedPreferences settingsShowAdditional) {
        boolean airPressureSwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_PRESSURE, false);
        boolean humiditySwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_HUMIDITY, false);
        boolean windSpeedSwitch = settingsShowAdditional.getBoolean(SETTINGS_SWITCH_WIND, false);
        switchShowAirPressure = view.findViewById(R.id.switchShowAirPressure);
        switchShowHumidity = view.findViewById(R.id.switchShowHumidity);
        switchShowWindSpeed = view.findViewById(R.id.switchShowWindSpeed);
        if (airPressureSwitch) {
            switchShowAirPressure.setChecked(true);
            String msg = String.valueOf(airPressureSwitch);
            createToast(msg);
        }
        if (humiditySwitch) {
            switchShowHumidity.setChecked(true);
            String msg = String.valueOf(humiditySwitch);
            createToast(msg);
        }
        if (windSpeedSwitch) {
            switchShowWindSpeed.setChecked(true);
            String msg = String.valueOf(windSpeedSwitch);
            createToast(msg);
        }


    }

    private void initializeSwitches(View view) {
        switchShowAirPressure = view.findViewById(R.id.switchShowAirPressure);
        switchShowHumidity = view.findViewById(R.id.switchShowHumidity);
        switchShowWindSpeed = view.findViewById(R.id.switchShowWindSpeed);
    }

    private void initSwitchListeners() {
       settingsShowAdditional = requireContext().getSharedPreferences(SHARED_PREF_ADD_SHOW, MODE_PRIVATE);
       switchShowAirPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_PRESSURE, isChecked);
               editor.apply();
               String msg = String.valueOf(isChecked);
               createToast(msg);
            }
       });

       switchShowHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_HUMIDITY, isChecked);
               editor.apply();
               String msg = String.valueOf(isChecked);
               createToast(msg);

           }
       });

       switchShowWindSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_WIND, isChecked);
               editor.apply();
               String msg = String.valueOf(isChecked);
               createToast(msg);
           }
       });

    }

    public void createToast(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
