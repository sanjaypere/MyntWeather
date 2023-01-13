package com.mynt.weather.db

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mynt.weather.getOrAwaitValue
import com.mynt.weather.models.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
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
        assertEquals(1, result.size)
        assertEquals("san@pere.com", result[0].email)
    }

    @Test
    fun getUserByValidEmailAndPassword_expectedSingleUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pere.com","1234")
        assertEquals("san@pere.com", result?.email)
    }

    @Test
    fun getUserByInValidEmail_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pre.com","1234")
        assertNull(result)
    }

    @Test
    fun getUserByInValidPassword_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        val result = userDao.getUserByEmailAndPassword("san@pere.com","134")
        assertNull(result)
    }

    @Test
    fun deleteUser_expectedNoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)

        userDao.deleteUser(user)

        val result = userDao.getAllUser().getOrAwaitValue()
        assertEquals(0, result.size)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun registerExistingUser_expectedException() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)
        val user1 = User(id = 2, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user1)
    }

    @Test
    fun getAllUser_expectedTwoUser() = runBlocking {
        val user = User(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        userDao.registerUser(user)
        val user1 = User(id = 2, name = "Sanjay", email = "san1@pere.com", password = "1234")
        userDao.registerUser(user1)

        val result = userDao.getAllUser().getOrAwaitValue()
        assertEquals(2, result.size)
    }

    @After
    fun tearDown() {
        db.close()
    }
}