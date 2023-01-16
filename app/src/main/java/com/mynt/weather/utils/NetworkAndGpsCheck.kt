package com.mynt.weather.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mynt.weather.BuildConfig
import com.mynt.weather.R

fun Activity.isLocationServiceOn(): Boolean {
    val locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var gpsEnabled = false
    var networkEnabled = false
    try {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (ex: Exception) {
        if (BuildConfig.IS_DEBUG) {
            ex.printStackTrace()
        }
    }

    try {
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (ex: Exception) {
        if (BuildConfig.IS_DEBUG) {
            ex.printStackTrace()
        }
    }
    if (gpsEnabled || networkEnabled) {
        return true
    }

    val alertDialog = AlertDialog.Builder(this)
    alertDialog.setTitle(resources.getString(R.string.location_service))
    alertDialog.setMessage(resources.getString(R.string.enable_location_service))
    alertDialog.setPositiveButton(resources.getString(R.string.settings)) { _, _ ->
        val intent = Intent(
            Settings.ACTION_LOCATION_SOURCE_SETTINGS
        )
        startActivity(intent)
    }
    alertDialog.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
    alertDialog.show()
    return false
}


fun Fragment.isNetworkAvailable(): Boolean {
    if (activity == null) {
        return true
    }
    val isNetworkAvailable = isNetworkAvailable(requireActivity())
    if (!isNetworkAvailable) {
        Toast.makeText(
            activity,
            resources.getString(R.string.no_network_available),
            Toast.LENGTH_SHORT
        ).show()
    }
    return isNetworkAvailable
}

fun isNetworkAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        @Suppress("Deprecation")
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}