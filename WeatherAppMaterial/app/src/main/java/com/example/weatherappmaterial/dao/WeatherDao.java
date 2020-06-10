package com.example.weatherappmaterial.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherappmaterial.model.WeatherHistory;

import java.util.List;

public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherHistory(WeatherHistory weatherHistory);

    @Update
    void updateWeatherHistory(WeatherHistory weatherHistory);

    @Delete
    void deleteWeatherHistory(WeatherHistory weatherHistory);

    @Query("delete from weatherhistory where id = :id")
    void deleteWeatherHistoryById(long id);

    @Query("select * from weatherhistory")
    List<WeatherHistory> getAllWeatherHistory();

    @Query("select count() from weatherhistory")
    long getCountWeatherHistory();

}
