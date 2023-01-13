package com.mynt.weather.di

import com.mynt.weather.db.UserDao
import com.mynt.weather.repository.LoginRepository
import com.mynt.weather.repositoryimp.LoginRepositoryImp
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
    fun provideLoginRepository(userDao: UserDao):LoginRepository = LoginRepositoryImp(userDao)
}