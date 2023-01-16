package com.mynt.weather.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.mynt.weather.R
import com.mynt.weather.databinding.ActivityRegistrationBinding
import com.mynt.weather.utils.MClickListener
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import com.mynt.weather.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity(), MClickListener {
    private lateinit var _binding: ActivityRegistrationBinding
    private val viewModel by viewModels<RegistrationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
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
                    Toast.makeText(this, Constants.userRegisteredSuccessfully, Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                is AppResponse.Error -> {
                    it.message?.let { v ->
                        Toast.makeText(this, v, Toast.LENGTH_LONG).show()
                    }
                }
                else -> {}
            }
        }
    }

    override fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.buttonRegister -> {
                viewModel.user.value?.let { viewModel.registerUser(it) }
            }
        }
    }
}