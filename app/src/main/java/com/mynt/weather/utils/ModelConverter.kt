package com.mynt.weather.utils

import com.mynt.weather.data.db.entity.WeatherEntity
import com.mynt.weather.data.models.OpenWeatherResponse

object ModelConverter {
    fun getWeatherEntityFromOpenWeatherResponse(
        userId: Int,
        response: OpenWeatherResponse
    ): WeatherEntity {
        val weather = response.weather?.get(0)
        return WeatherEntity(
            weather = weather?.main,
            city = response.name,
            country = response.sys?.country,
            icon = weather?.icon,
            weatherDescription = weather?.description,
            sunrise = response.sys?.sunrise,
            sunset = response.sys?.sunset,
            temp = response.main?.temp,
            createdTime = System.currentTimeMillis(),
            userId = userId
        )
    }
}