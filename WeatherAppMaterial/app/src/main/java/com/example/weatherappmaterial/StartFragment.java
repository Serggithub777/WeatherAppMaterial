package com.example.weatherappmaterial;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class StartFragment extends Fragment {
    private Button buttonShowWeather;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonShowWeather = view.findViewById(R.id.buttonShowWeather);
        LinearLayout viewRecentCitiesList = view.findViewById(R.id.recent_cities_List_linear_layout);
        TextInputLayout viewTextInputLayoutEnterCity = view.findViewById(R.id.textInputLayoutEnterCity);
        onClickListenerShowWeather(viewTextInputLayoutEnterCity);
        initListRecentCities(viewRecentCitiesList);
    }

    private void onClickListenerShowWeather(final View viewTextInputLayoutEnterCity) {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText textInputCityEnter = viewTextInputLayoutEnterCity.findViewById(R.id.textInputCityEnter);
                String cityName = Objects.requireNonNull(textInputCityEnter.getText()).toString().trim();
                if (cityName.isEmpty()) {
                    Toast.makeText(getContext(), "Enter City Name!", Toast.LENGTH_SHORT).show();
                } else {
                    NavHostFragment.findNavController(StartFragment.this)
                            .navigate(R.id.action_startFragment_to_weatherFragment);
                }
            }
        });
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

}
