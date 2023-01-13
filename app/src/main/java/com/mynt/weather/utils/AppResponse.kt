package com.mynt.weather.utils

sealed class AppResponse() {
    class Success : AppResponse()
    class Error(val message: String? = null) : AppResponse()
    class Loading : AppResponse()
}

