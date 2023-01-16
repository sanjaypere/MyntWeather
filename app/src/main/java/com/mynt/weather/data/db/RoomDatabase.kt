package com.mynt.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mynt.weather.data.db.dao.UserDao
import com.mynt.weather.data.db.dao.WeatherDao
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.data.db.entity.WeatherEntity

@Database(entities = [UserEntity::class, WeatherEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getWeatherDao(): WeatherDao
}