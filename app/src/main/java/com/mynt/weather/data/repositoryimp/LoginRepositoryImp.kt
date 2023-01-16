package com.mynt.weather.data.repositoryimp

import com.mynt.weather.data.db.dao.UserDao
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.data.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val userDao: UserDao) : LoginRepository {
    override suspend fun registerUser(user: UserEntity) {
        userDao.registerUser(user)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}