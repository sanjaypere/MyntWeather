package com.mynt.weather.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mynt.weather.data.db.entity.WeatherEntity
import com.mynt.weather.databinding.FragmentHistoryBinding
import com.mynt.weather.ui.adapter.HistoryAdapter
import com.mynt.weather.viewmodel.HistoryViewModel
import com.mynt.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var _binding: FragmentHistoryBinding
    private val historyViewModel by activityViewModels<HistoryViewModel>()
    private val weatherViewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.vm = historyViewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherObserver()
    }

    private fun setWeatherObserver() {
        weatherViewModel.user?.id?.let { userId ->
            historyViewModel.getWeatherHistory(userId).observe(viewLifecycleOwner) {
                _binding.noHistoryAvailableTextView.visibility =
                    if (it.isNotEmpty()) View.GONE else View.VISIBLE
                setHistoryAdapter(it)
            }
        }
    }

    private fun setHistoryAdapter(schoolList: List<WeatherEntity>) {
        _binding.historyRecyclerView.layoutManager = LinearLayoutManager(activity)
        _binding.historyRecyclerView.adapter =
            HistoryAdapter(requireContext(), schoolList)
    }
}