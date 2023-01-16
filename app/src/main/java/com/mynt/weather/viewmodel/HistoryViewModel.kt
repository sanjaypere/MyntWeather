package com.mynt.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.mynt.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    /**
     * Fetch saved weather list from local database
     */
    fun getWeatherHistory(userId: Int) = weatherRepository.getAllWeatherByUserId(userId)
}