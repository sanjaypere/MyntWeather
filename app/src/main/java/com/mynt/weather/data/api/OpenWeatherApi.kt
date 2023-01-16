package com.mynt.weather.data.api

import com.mynt.weather.data.models.OpenWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface OpenWeatherApi {
    @GET
    suspend fun getWeatherDetail(@Url url: String): Response<OpenWeatherResponse>
}