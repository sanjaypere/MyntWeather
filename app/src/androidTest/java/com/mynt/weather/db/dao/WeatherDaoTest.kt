package com.mynt.weather.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mynt.weather.data.db.RoomDatabase
import com.mynt.weather.data.db.dao.WeatherDao
import com.mynt.weather.getOrAwaitValue
import com.mynt.weather.data.db.entity.WeatherEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabase
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDatabase::class.java
        ).allowMainThreadQueries().build()
        weatherDao = db.getWeatherDao()
    }

    @Test
    fun insertWeather_expectedSingleWeather() = runBlocking {
        val weather =
            WeatherEntity(
                id = 1,
                userId = 1,
                weather = "Rain",
                sunrise = 1661834187,
                sunset = 1661882248
            )
        weatherDao.insertWeather(weather)

        val result = weatherDao.getAllWeatherByUserId(1).getOrAwaitValue()
        assertEquals(1, result.size)
        assertEquals("Rain", result[0].weather)
    }

    @Test
    fun getAllWeatherByUserId_expectedTwoWeather() = runBlocking {
        val weather =
            WeatherEntity(
                id = 1,
                userId = 1,
                weather = "Rain",
                sunrise = 1661834187,
                sunset = 1661882248
            )
        val weather1 =
            WeatherEntity(
                id = 2,
                userId = 1,
                weather = "Sunny",
                sunrise = 1661834187,
                sunset = 1661882248
            )

        val weather2 =
            WeatherEntity(
                id = 3,
                userId = 2,
                weather = "Sunny",
                sunrise = 1661834187,
                sunset = 1661882248
            )

        weatherDao.insertWeather(weather)
        weatherDao.insertWeather(weather1)
        weatherDao.insertWeather(weather2)

        val result = weatherDao.getAllWeatherByUserId(1).getOrAwaitValue()
        assertEquals(2, result.size)
    }

    @After
    fun tearDown() {
        db.close()
    }
}