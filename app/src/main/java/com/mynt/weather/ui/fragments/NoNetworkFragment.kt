package com.mynt.weather.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mynt.weather.R
import com.mynt.weather.databinding.FragmentNoNetworkBinding
import com.mynt.weather.utils.MClickListener
import com.mynt.weather.utils.isNetworkAvailable
import com.mynt.weather.viewmodel.WeatherViewModel

class NoNetworkFragment : Fragment(), MClickListener {
    private lateinit var _binding: FragmentNoNetworkBinding
    private val viewModel by activityViewModels<WeatherViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoNetworkBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.listener = this
        return _binding.root
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.retryAgainButton -> {
                if (isNetworkAvailable()) {
                    viewModel.updateNetworkState(true)
                }
            }
        }
    }

}