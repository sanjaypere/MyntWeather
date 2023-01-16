package com.mynt.weather.di

import android.content.Context
import androidx.room.Room
import com.mynt.weather.BuildConfig
import com.mynt.weather.R
import com.mynt.weather.data.db.RoomDatabase
import com.mynt.weather.data.db.dao.UserDao
import com.mynt.weather.data.db.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideUserDao(roomDatabase: RoomDatabase): UserDao = roomDatabase.getUserDao()

    @Provides
    @Singleton
    fun provideWeatherDao(roomDatabase: RoomDatabase): WeatherDao = roomDatabase.getWeatherDao()

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase {
        val supportFactory =
            SupportFactory(SQLiteDatabase.getBytes(BuildConfig.passCode.toCharArray()))
        return Room.databaseBuilder(
            context, RoomDatabase::class.java, context.resources.getString(R.string.app_name)
        ).openHelperFactory(supportFactory).build()
    }
}