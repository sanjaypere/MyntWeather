package com.mynt.weather.di

import com.mynt.weather.data.db.dao.UserDao
import com.mynt.weather.data.db.dao.WeatherDao
import com.mynt.weather.data.repository.LoginRepository
import com.mynt.weather.data.repository.WeatherRepository
import com.mynt.weather.data.repositoryimp.LoginRepositoryImp
import com.mynt.weather.data.repositoryimp.WeatherRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(userDao: UserDao): LoginRepository = LoginRepositoryImp(userDao)

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDao: WeatherDao): WeatherRepository =
        WeatherRepositoryImp(weatherDao)
}