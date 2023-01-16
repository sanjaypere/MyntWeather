package com.mynt.weather.data.repository

import com.mynt.weather.data.db.entity.UserEntity

interface LoginRepository {
    suspend fun registerUser(user: UserEntity)
    suspend  fun getUserByEmailAndPassword(email: String,password:String): UserEntity?
}