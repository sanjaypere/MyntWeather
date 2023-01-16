package com.mynt.weather.data.models

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val oneH: Double? = null
)
