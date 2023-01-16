package com.mynt.weather.data.repository

import com.mynt.weather.utils.AppResponse

interface ApiRepository {
    suspend fun getWeatherDetail(url: String): AppResponse
}