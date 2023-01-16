package com.mynt.weather.data.models

data class OpenWeatherResponse(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Long? = null,
    val id: Long? = null,
    val main: Main? = null,
    val name: String? = null,
    val rain: Rain? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
)
