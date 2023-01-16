package com.mynt.weather.data.repositoryimp

import androidx.lifecycle.LiveData
import com.mynt.weather.data.db.dao.WeatherDao
import com.mynt.weather.data.db.entity.WeatherEntity
import com.mynt.weather.data.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(val weatherDao: WeatherDao) : WeatherRepository {
    override suspend fun insertWeather(weather: WeatherEntity) {
        weatherDao.insertWeather(weather)
    }

    override fun getAllWeatherByUserId(userId:Int): LiveData<List<WeatherEntity>> {
        return weatherDao.getAllWeatherByUserId(userId)
    }
}