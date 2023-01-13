package com.mynt.weather.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mynt.weather.models.User

@Database(entities = [User::class], version = 1)
abstract class RoomDatabase :RoomDatabase() {
    abstract fun getUserDao(): UserDao
}