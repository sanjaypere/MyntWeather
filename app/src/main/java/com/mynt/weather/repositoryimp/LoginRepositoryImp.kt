package com.mynt.weather.repositoryimp

import androidx.lifecycle.LiveData
import com.mynt.weather.db.UserDao
import com.mynt.weather.models.User
import com.mynt.weather.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val userDao: UserDao) :LoginRepository{
    override suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    override fun getAllUser(): LiveData<List<User>> {
     return userDao.getAllUser()
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email,password)
    }

}