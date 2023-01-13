package com.mynt.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mynt.weather.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM user where email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user where email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String,password:String): User?

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)

}