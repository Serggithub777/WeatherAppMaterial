package com.example.weatherappmaterial;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements FragmentDialogResult {

    public static final String SHARED_PREF_THEME = "light_dark_theme_prefer";
    public static final String SETTINGS_SWITCH_THEME = "switch_theme";
    private View view;
    StartFragmentDialog startFragmentDialog;
    SharedPreferences settingsTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settingsTheme = getSharedPreferences(SHARED_PREF_THEME,MODE_PRIVATE);
        boolean switchTheme = settingsTheme.getBoolean(SETTINGS_SWITCH_THEME, false);
        if (switchTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view = findViewById(R.id.nav_host_fragment);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settingsFragment) {
            Navigation.findNavController(view).navigate(R.id.settingsFragment);
            return true;
        }
        if (id == R.id.historyFragment2) {
            Navigation.findNavController(view).navigate(R.id.historyFragment2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDialogResult(String result) {

    }
    //NavHostFragment.findNavController(WeatherFragment.this).navigate(R.id.action_weatherFragment_to_startFragment);

}
