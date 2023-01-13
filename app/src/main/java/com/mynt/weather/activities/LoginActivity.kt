package com.mynt.weather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mynt.weather.R
import com.mynt.weather.databinding.ActivityLoginBinding
import com.mynt.weather.interfaces.MClickListener
import com.mynt.weather.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), MClickListener {
    private lateinit var _binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.lifecycleOwner = this
        _binding.vm = viewModel
        _binding.listener = this
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                viewModel.user.value?.let { viewModel.proceedToLogin(it) }
            }
        }
    }
}