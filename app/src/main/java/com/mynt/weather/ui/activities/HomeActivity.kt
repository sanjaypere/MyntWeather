package com.mynt.weather.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mynt.weather.R
import com.mynt.weather.models.User
import com.mynt.weather.utils.Constants

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Constants.dataKey, User::class.java)
        } else {
            intent.getSerializableExtra(Constants.dataKey)
        }

        Log.d("Test",user.toString())
    }
}