package com.example.weatherappmaterial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final String TAG = "WEATHER";
    public static final String SHARED_PREF_ADD_SHOW = "show_additional_prefer";
    public static final String SETTINGS_SWITCH_PRESSURE = "switch_pressure";
    public static final String SETTINGS_SWITCH_HUMIDITY = "switch_humidity";
    public static final String SETTINGS_SWITCH_WIND = "switch_wind";

    public static final String SHARED_PREF_THEME = "light_dark_theme_prefer";
    public static final String SETTINGS_SWITCH_THEME = "switch_theme";

    public static final String SHARED_PREF_TEMP_MESUR = "temp_mesure_pref";
    public static final String TEMP_RADIOBUTTON_INDEX = "temp_radiobutton_index";
    // Required empty public constructor
    SharedPreferences settingsShowAdditional;
    SharedPreferences settingsTheme;
    SharedPreferences settingsTempMesur;
    Switch switchShowAirPressure;
    Switch switchShowHumidity;
    Switch switchShowWindSpeed;
    Switch switchDarkLightTheme;
    RadioGroup radioGroupSetTempMesur;


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
        SharedPreferences settingsTheme = requireContext().getSharedPreferences(SHARED_PREF_THEME, MODE_PRIVATE);
        if (settingsTheme != null) {
            loadThemePreferences(view, settingsTheme);
        }
        SharedPreferences settingsTempMesur = requireContext().getSharedPreferences(SHARED_PREF_TEMP_MESUR, MODE_PRIVATE);
        if (settingsTempMesur != null) {
            loadTempMesurPreferences(view, settingsTempMesur);
        }
        initializeSwitches(view);
        initSwitchListeners();

    }

    private void loadTempMesurPreferences(View view, SharedPreferences settingsTempMesur) {
        radioGroupSetTempMesur = view.findViewById(R.id.radioGroupSetTempMesur);
        int saveRadioButtonIndex = settingsTempMesur.getInt(TEMP_RADIOBUTTON_INDEX, 0);
        RadioButton savedCheckedRadiobutton = (RadioButton) radioGroupSetTempMesur.getChildAt(saveRadioButtonIndex);
        savedCheckedRadiobutton.setChecked(true);

    }

    private void loadThemePreferences(View view, SharedPreferences settingsTheme) {
        boolean themeSwitch = settingsTheme.getBoolean(SETTINGS_SWITCH_THEME, false);
        switchDarkLightTheme = view.findViewById(R.id.switchDarkLigthTheme);
        if (themeSwitch) {
            switchDarkLightTheme.setChecked(true);
        }
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
        }
        if (humiditySwitch) {
            switchShowHumidity.setChecked(true);
        }
        if (windSpeedSwitch) {
            switchShowWindSpeed.setChecked(true);
        }


    }

    private void initializeSwitches(View view) {
        switchShowAirPressure = view.findViewById(R.id.switchShowAirPressure);
        switchShowHumidity = view.findViewById(R.id.switchShowHumidity);
        switchShowWindSpeed = view.findViewById(R.id.switchShowWindSpeed);
        switchDarkLightTheme = view.findViewById(R.id.switchDarkLigthTheme);
        radioGroupSetTempMesur = view.findViewById(R.id.radioGroupSetTempMesur);
    }

    private void initSwitchListeners() {
       settingsShowAdditional = requireContext().getSharedPreferences(SHARED_PREF_ADD_SHOW, MODE_PRIVATE);
       switchShowAirPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_PRESSURE, isChecked);
               editor.apply();
               if (isChecked) {
                   createToast("air pressure is displayed");
               } else  createToast("air pressure is not displayed");
            }
       });

       switchShowHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_HUMIDITY, isChecked);
               editor.apply();
               if (isChecked) {
                   createToast("humidity is displayed");
               } else  createToast("humidity is not displayed");

           }
       });

       switchShowWindSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsShowAdditional.edit();
               editor.putBoolean(SETTINGS_SWITCH_WIND, isChecked);
               editor.apply();
               if (isChecked) {
                   createToast("wind speed is displayed");
               } else  createToast("wind speed is not displayed");

           }
       });
       settingsTheme = requireContext().getSharedPreferences(SHARED_PREF_THEME, MODE_PRIVATE);
       switchDarkLightTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               SharedPreferences.Editor editor = settingsTheme.edit();
               editor.putBoolean(SETTINGS_SWITCH_THEME, isChecked);
               editor.apply();
               if (isChecked) {
                   createToast("dark theme set");
               } else  createToast("light theme set");
               restart();
           }
       });

        settingsTempMesur = requireContext().getSharedPreferences(SHARED_PREF_TEMP_MESUR, MODE_PRIVATE);
        radioGroupSetTempMesur.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = radioGroupSetTempMesur.findViewById(checkedId);
                int checkedIndex = radioGroupSetTempMesur.indexOfChild(checkedRadioButton);
                SharedPreferences.Editor editor = settingsTempMesur.edit();
                editor.putInt(TEMP_RADIOBUTTON_INDEX, checkedIndex);
                editor.apply();
            }
        });
    }

    private void restart() {
        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(
                getContext().getPackageName() );
        intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void createToast(String msg) {
        Log.d(TAG, msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
