package com.example.weatherappmaterial;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappmaterial.dao.WeatherDao;

public class HistoryFragment extends Fragment {
    private HistoryRecyclerAdapter adapter;
    private WeatherSource weatherSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initHistoryRecyclerView(view);
    }

    private void initHistoryRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.historyRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        WeatherDao weatherDao = App.getInstance().getWeatherDao();
        weatherSource = new WeatherSource(weatherDao);

        adapter = new HistoryRecyclerAdapter(this, weatherSource);
        recyclerView.setAdapter(adapter);

    }
}
