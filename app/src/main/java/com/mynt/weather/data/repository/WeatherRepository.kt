package com.mynt.weather.data.repository

import androidx.lifecycle.LiveData
import com.mynt.weather.data.db.entity.WeatherEntity

interface WeatherRepository {
    suspend fun insertWeather(weather: WeatherEntity)
    fun getAllWeatherByUserId(userId:Int):LiveData<List<WeatherEntity>>
}