package com.mynt.weather.utils

import com.mynt.weather.data.models.Weather
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    /**
     * @param : weathers - List of weathers
     * @return : String - Return the icon for the very first object of the list
     */
    fun getImageIcon(weathers: List<Weather>?): String {
        return weathers?.get(0)?.icon ?: Constants.defaultWeatherIcon
    }

    /**
     * @param : weathers - List of weathers
     * @return : String - Return the weather for the very first object of the list
     */
    fun getWeather(weathers: List<Weather>?): String {
        return weathers?.get(0)?.main ?: ""
    }

    /**
     * @param : kelvinTemp - Temperature in Kelvin
     * @return : String - Temperature in Celsius
     */
    fun kelvinToCelsius(kelvinTemp: Double?): String {
        return kelvinTemp?.minus(273.15)?.let { getTwoDigitAfterPoint(it) } ?: "-"
    }

    /**
     * @param : sunTime - Sunrise or Sunset Time
     * @return : String - Formatted time in hh:mm a
     */
    fun formatSolarTime(sunTime: Long?): String {
        val time = sunTime ?: (System.currentTimeMillis() / 1000)
        return getFromMillis((time * 1000), "hh:mm a")
    }

    /**
     * @param : createdAt - Time in milliseconds
     * @return : String - Formatted time in dd MMM yyyy, hh:mm a
     */
    fun formatHistoryCreatedAt(createdAt: Long?): String {
        val time = createdAt ?: System.currentTimeMillis()
        return getFromMillis(time, "dd MMM yyyy, hh:mm a")
    }

    /**
     * @param : value - Double value
     * @return : String - Format value with only two digit after decimal
     */
    private fun getTwoDigitAfterPoint(value: Double): String {
        return DecimalFormat("##.##").format(value)
    }

    /**
     * @param : time - Time in milliseconds
     * @param : format - Format of date time e.g MMM yyyy, hh:mm a
     * @return : String - Format date time
     */
    private fun getFromMillis(time: Long, format: String, locale: Locale = Locale.ENGLISH): String {
        val sdf = SimpleDateFormat(format, locale)
        return sdf.format(Date(time))
    }
}