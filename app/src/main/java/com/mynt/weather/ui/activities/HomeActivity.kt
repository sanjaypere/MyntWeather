package com.mynt.weather.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.mynt.weather.R
import com.mynt.weather.databinding.ActivityHomeBinding
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.ui.fragments.HistoryFragment
import com.mynt.weather.ui.fragments.NoNetworkFragment
import com.mynt.weather.ui.fragments.WeatherFragment
import com.mynt.weather.utils.Constants
import com.mynt.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityHomeBinding
    private val viewModel by viewModels<WeatherViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setUserName()
        setFragment(WeatherFragment())

        _binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.weatherMenu -> {
                    setFragment(WeatherFragment())
                }
                R.id.historyMenu -> {
                    setFragment(HistoryFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        setNetworkObserver()
    }

    private fun setUserName() {
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Constants.dataKey, UserEntity::class.java)
        } else {
            intent.getSerializableExtra(Constants.dataKey)
        }
        user?.let {
            viewModel.setUserEntity(it as UserEntity)
        }
    }

    private fun setNetworkObserver() {
        viewModel.isNetworkAvailable.observe(this) {
            when (it) {
                true -> {
                    setFragment(WeatherFragment())
                }
                else -> {
                    setFragment(NoNetworkFragment())
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    override fun onBackPressed() {
        val selectedItemId = _binding.bottomNavigationView.selectedItemId
        if (selectedItemId == R.id.historyMenu) {
            _binding.bottomNavigationView.selectedItemId = R.id.weatherMenu
            return
        }
        super.onBackPressed()
    }
}