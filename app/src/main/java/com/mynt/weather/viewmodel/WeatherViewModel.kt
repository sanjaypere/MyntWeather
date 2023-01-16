package com.mynt.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.data.models.OpenWeatherResponse
import com.mynt.weather.data.repository.ApiRepository
import com.mynt.weather.data.repository.WeatherRepository
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.ModelConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _appResponse = MutableLiveData<AppResponse>()
    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    private val _weatherResponse = MutableLiveData<OpenWeatherResponse>()
    val weatherResponse: LiveData<OpenWeatherResponse>
        get() = _weatherResponse
    val appResponse: LiveData<AppResponse>
        get() = _appResponse
    val isNetworkAvailable: LiveData<Boolean>
        get() = _isNetworkAvailable
    var user: UserEntity? = null

    /**
     * Set UserEntity(Logged in user detail)
     */
    fun setUserEntity(userEntity: UserEntity) {
        this.user = userEntity
    }

    /**
     * Update network availability state
     */
    fun updateNetworkState(isNetworkAvailable: Boolean) {
        _isNetworkAvailable.postValue(isNetworkAvailable)
    }

    /**
     *  Fetch weather for OpenWeather API and save to local database
     *  @param : url - OpenWeather API Url
     */
    fun getWeatherDetail(url: String) {
        _appResponse.postValue(AppResponse.Loading())
        viewModelScope.launch {
            when (val appResponse = apiRepository.getWeatherDetail(url)) {
                is AppResponse.Success -> {
                    if (appResponse.data is OpenWeatherResponse) {
                        appResponse.data.let {
                            saveWeatherResponseToDB(it)
                            _weatherResponse.postValue(it)
                        }
                    }
                    _appResponse.postValue(appResponse)
                }
                else -> {
                    _appResponse.postValue(appResponse)
                }
            }
        }
    }

    /**
     * Save weather response to local database
     */
    private suspend fun saveWeatherResponseToDB(response: OpenWeatherResponse) {
        user?.id?.let {
            weatherRepository.insertWeather(
                ModelConverter.getWeatherEntityFromOpenWeatherResponse(
                    it,
                    response
                )
            )
        }
    }
}