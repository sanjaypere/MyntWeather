package com.mynt.weather.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.mynt.weather.R
import com.mynt.weather.databinding.ActivityLoginBinding
import com.mynt.weather.utils.MClickListener
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import com.mynt.weather.utils.isLocationServiceOn
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
        setAppResponseObserver()
    }

    private fun setAppResponseObserver() {
        viewModel.appResponse.observe(this) {
            when (it) {
                is AppResponse.Success -> {
                    startActivity(Intent(this, HomeActivity::class.java).apply {
                        putExtra(Constants.dataKey, viewModel.user.value)
                    })
                    finish()
                }
                is AppResponse.Error -> {
                    it.message?.let { msg ->
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                    }
                }
                else -> {}
            }
        }
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                if (viewModel.appResponse.value !is AppResponse.Loading && isLocationServiceOn()) {
                    viewModel.user.value?.let { viewModel.proceedToLogin(it) }
                }
            }
            R.id.textViewRegisterNow -> {
                startActivity(Intent(this, RegistrationActivity::class.java))
            }
        }
    }
}