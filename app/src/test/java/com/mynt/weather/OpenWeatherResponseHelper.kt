package com.mynt.weather

import java.io.InputStreamReader

object OpenWeatherResponseHelper {
    /**
     * Get sample OpenWeather json response from file. The sample json is provided on OpenWeather portal
     */
    fun readJsonResourceFromFile(filename: String): String {
        val inputStream = OpenWeatherResponseHelper::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}