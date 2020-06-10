package com.example.weatherappmaterial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappmaterial.model.WeatherHistory;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private HistoryFragment historyFragment;
    private WeatherSource weatherSource;
    private long menuPosition;


    public HistoryRecyclerAdapter(HistoryFragment historyFragment, WeatherSource weatherSource) {
        this.historyFragment = historyFragment;
        this.weatherSource = weatherSource;
    }



    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_rcycler_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.ViewHolder holder, int position) {
        List<WeatherHistory> weatherHistories = weatherSource.getWeatherHistories();
        WeatherHistory weatherHistory = weatherHistories.get(position);
        holder.textRecyclerItemCityName.setText(weatherHistory.cityName);
        holder.textRecyclerItemDate.setText(weatherHistory.date);
        holder.textRecyclerItemTemp.setText(weatherHistory.temp);

        holder.cardView.setOnLongClickListener(v -> {
            menuPosition = position;
            return false;
        });

        if (historyFragment != null) {
            historyFragment.registerForContextMenu(holder.cardView);
        }

    }

    @Override
    public int getItemCount() {
        return (int) weatherSource.getCountWeatherHistories();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRecyclerItemCityName;
        TextView textRecyclerItemDate;
        TextView textRecyclerItemTemp;
        View cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView;
            textRecyclerItemCityName = cardView.findViewById(R.id.textRecyclerItemCityName);
            textRecyclerItemDate = cardView.findViewById(R.id.textRecyclerItemDate);
            textRecyclerItemTemp = cardView.findViewById(R.id.textRecyclerItemTemp);

        }
    }
}
