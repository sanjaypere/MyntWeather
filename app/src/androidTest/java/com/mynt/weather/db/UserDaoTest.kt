package com.mynt.weather.db

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mynt.weather.getOrAwaitValue
import com.mynt.weather.models.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = db.getUserDao()
    }

    @Test
    fun registerUser_expectedSingleUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getAllUser().getOrAwaitValue()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("san@pere.com", result[0].email)
    }

    @Test
    fun getUserByValidEmailAndPassword_expectedSingleUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pere.com","1234")
        Assert.assertEquals("san@pere.com", result?.email)
    }

    @Test
    fun getUserByInValidEmail_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pre.com","1234")
        Assert.assertNull(result)
    }

    @Test
    fun getUserByInValidPassword_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pere.com","134")
        Assert.assertNull(result)
    }

    @Test
    fun deleteUser_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        userDao.deleteUser(user)

        val result = userDao.getAllUser().getOrAwaitValue()
        Assert.assertEquals(0, result.size)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun registerExistingUser_expectedException() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)
        val user1 = User(id = 2, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun getAllUser_expectedTwoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)
        val user1 = User(id = 2, name = "Sanjay", email = "san1@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getAllUser().getOrAwaitValue()
        Assert.assertEquals(2, result.size)
    }

    @After
    fun tearDown() {
        db.close()
    }
}