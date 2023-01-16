package com.mynt.weather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val userId: Int? = null,
    val weather: String? = null,
    val weatherDescription: String? = null,
    val icon: String? = null,
    val city: String? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double? = null,
    val createdTime: Long? = null,
)