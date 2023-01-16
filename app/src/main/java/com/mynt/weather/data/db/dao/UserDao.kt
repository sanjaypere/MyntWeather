package com.mynt.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mynt.weather.data.db.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM user where email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String,password:String): UserEntity?

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Delete
    suspend fun deleteUser(user: UserEntity)

}