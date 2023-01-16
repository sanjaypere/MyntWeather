package com.mynt.weather.di

import com.mynt.weather.BuildConfig
import com.mynt.weather.data.api.OpenWeatherApi
import com.mynt.weather.data.repository.ApiRepository
import com.mynt.weather.data.repositoryimp.ApiRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepository(openWeatherApi: OpenWeatherApi): ApiRepository =
        ApiRepositoryImp(openWeatherApi)
}