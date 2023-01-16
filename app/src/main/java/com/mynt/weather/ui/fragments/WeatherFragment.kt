package com.mynt.weather.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.LocationServices
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.mynt.weather.R
import com.mynt.weather.databinding.FragmentWeatherBinding
import com.mynt.weather.utils.*
import com.mynt.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class WeatherFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var _binding: FragmentWeatherBinding
    private val viewModel by activityViewModels<WeatherViewModel>()
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.vm = viewModel
        _binding.util = Utils
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressIndicator = _binding.progressIndicator
        progressIndicator.hide()
        _binding.userNameTextView.text =
            String.format(
                resources.getString(R.string.userName),
                viewModel.user?.name ?: Constants.guest
            )
        setAppResponseObserver()
        checkLocationPermissionAndFetchWeather()
    }

    private fun setAppResponseObserver() {
        viewModel.appResponse.observe(viewLifecycleOwner) {
            when (it) {
                is AppResponse.Success -> {
                    progressIndicator.hide()
                }
                is AppResponse.Error -> {
                    it.message?.let { msg ->
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                    }
                    progressIndicator.hide()
                }
                is AppResponse.Loading -> {
                    progressIndicator.show()
                }
            }
        }
    }

    private fun checkLocationPermissionAndFetchWeather() {
        if (hasLocationPermission()) {
            getWeatherDetail()
        } else {
            EasyPermissions.requestPermissions(
                this,
                resources.getString(R.string.locationPermissionMsg),
                Constants.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }

    }

    private fun getWeatherDetail() {
        if (viewModel.weatherResponse.value == null) {
            if (isNetworkAvailable()) {
                if (activity?.isLocationServiceOn() == true) {
                    getLatKnownLocation()
                }
            } else {
                viewModel.updateNetworkState(false)
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getWeatherDetail()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            checkLocationPermissionAndFetchWeather()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLatKnownLocation() {
        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
            if (it != null) {
                viewModel.getWeatherDetail(UrlUtils.getOpenWeatherUrl(it))
            }
        }
    }
}