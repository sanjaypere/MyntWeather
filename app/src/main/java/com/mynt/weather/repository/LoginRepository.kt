package com.mynt.weather.repository

import androidx.lifecycle.LiveData
import com.mynt.weather.models.User

interface LoginRepository {
    suspend fun registerUser(user:User)
    suspend fun getUserByEmail(email:String):User?
    fun getAllUser():LiveData<List<User>>
    suspend  fun getUserByEmailAndPassword(email: String,password:String): User?
}