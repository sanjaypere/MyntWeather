package com.mynt.weather.data.repositoryimp

import com.mynt.weather.data.api.OpenWeatherApi
import com.mynt.weather.data.repository.ApiRepository
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import javax.inject.Inject

class ApiRepositoryImp @Inject constructor(private val openWeatherApi: OpenWeatherApi) :
    ApiRepository {
    override suspend fun getWeatherDetail(url: String): AppResponse {
        val response = openWeatherApi.getWeatherDetail(url)
        return if (response.isSuccessful) {
            AppResponse.Success(data = response.body())
        } else {
            AppResponse.Error(message = Constants.somethingWentWrong)
        }
    }
}