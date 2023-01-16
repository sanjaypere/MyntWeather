package com.mynt.weather.utils

import com.mynt.weather.data.models.Weather

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class UtilsTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetImageIcon_expectedImageIconName() {
        val result = Utils.getImageIcon(listOf(Weather(icon = "02d"), Weather(icon = "03d")))
        assertEquals("02d", result)
    }

    @Test
    fun testGetImageIconWithNull_expectedDefaultImageIconName() {
        val result = Utils.getImageIcon(null)
        assertEquals("01d", result)
    }

    @Test
    fun testGetWeather_expectedWeatherName() {
        val result = Utils.getWeather(listOf(Weather(main = "Rain"), Weather(icon = "Sunny")))
        assertEquals("Rain", result)
    }

    @Test
    fun testGetWeatherWithNull_expectedEmptyString() {
        val result = Utils.getWeather(null)
        assertEquals("", result)
    }

    @Test
    fun testKelvinToCelsius_expectedTemperature() {
        val result = Utils.kelvinToCelsius(300.01)
        assertEquals("26.86", result)
    }

    @Test
    fun testKelvinToCelsiusWithNull_expectedZeroTemperature() {
        val result = Utils.kelvinToCelsius(null)
        assertEquals("0", result)
    }

    @Test
    fun testFormatHistoryCreatedAt_expectedValidDate() {
        val result = Utils.formatHistoryCreatedAt(1673837774544)
        assertEquals("16 Jan 2023, 08:26 AM", result)
    }

    @Test
    fun testFormatHistoryCreatedAtWithNull_expectedValidDate() {
        val result = Utils.formatHistoryCreatedAt(null).isNotEmpty()
        assertTrue(result)
    }

    @Test
    fun testFormatSolarTime_expectedValidHourMinute() {
        val result = Utils.formatSolarTime(1661834187)
        assertEquals("10:06 AM", result)
    }

    @Test
    fun testFormatSolarTimeWithNull_expectedValidHourMinute() {
        val result = Utils.formatSolarTime(null).isNotEmpty()
        assertTrue(result)
    }
}