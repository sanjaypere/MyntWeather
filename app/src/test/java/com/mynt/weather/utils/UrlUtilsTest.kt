package com.mynt.weather.utils

import android.location.Location
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UrlUtilsTest {
    @Mock
    lateinit var location: Location

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetImageUrl_expectedImageUrl() {
        val result = UrlUtils.getImageUrl("test")
        assertEquals("https://openweathermap.org/img/wn/test@4x.png", result)
    }

    @Test
    fun testGetOpenWeatherUrl_expectedOpenWeatherUr() {
        Mockito.`when`(location.latitude).thenReturn(104645.01)
        Mockito.`when`(location.longitude).thenReturn(634310.09)
        val result = UrlUtils.getOpenWeatherUrl(location)
            .startsWith("https://api.openweathermap.org/data/2.5/weather?lat=104645.01&lon=634310.09&appid=")
        assertTrue(result)
    }
}