package com.mynt.weather.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var name: String? = null,
    var email: String,
    var password: String
):java.io.Serializable
