package com.mynt.weather.utils

import android.location.Location
import com.mynt.weather.BuildConfig

object UrlUtils {
    /**
     * @param : icon name
     * @return : weather image full url
     */
    fun getImageUrl(iconName: String): String {
        return "${BuildConfig.BASE_URL_ICON}${iconName}@4x.png"
    }

    /**
     * @params : location: Location object need to get longitude and latitude
     * @return : OpenWeather full url
     */
    fun getOpenWeatherUrl(location: Location): String {
        return "${BuildConfig.BASE_URL}weather?lat=${location.latitude}&lon=${location.longitude}&appid=${BuildConfig.openWeatherApiKey}"
    }
}