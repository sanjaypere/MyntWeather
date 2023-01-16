package com.mynt.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mynt.weather.data.db.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Insert
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather where userId = :userId ORDER BY id DESC")
    fun getAllWeatherByUserId(userId: Int): LiveData<List<WeatherEntity>>

}