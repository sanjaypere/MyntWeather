package com.mynt.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mynt.weather.getOrAwaitValue
import com.mynt.weather.models.User
import com.mynt.weather.repositoryimp.LoginRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.Dispatcher
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repository: LoginRepositoryImp

    lateinit var vm: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        vm = LoginViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun validateValidEmail_expectedTrue() {
        val result = vm.isValidEmail("sanjya@gmail.com")
        assertEquals(true, result)
    }

    @Test
    fun validateInvalidEmail_expectedFalse() {
        val result = vm.isValidEmail("sanjaygmail.com")
        assertEquals(false, result)
        val result1 = vm.isValidEmail("sanjay@gmailcom")
        assertEquals(false, result1)
    }

    @Test
    fun validateValidPassword_expectedTrue() {
        val result = vm.isValidPassword("Sanjay1234")
        assertEquals(true, result)
    }

    @Test
    fun validateInvalidPassword_expectedFalse() {
        val result = vm.isValidPassword("San")
        assertEquals(false, result)
    }

    @Test
    fun validateLoginWithValidCredential_expectedSanjay() = runTest {
        Mockito.`when`(
            repository.getUserByEmailAndPassword(
                email = "sanjay@gmail.com",
                password = "Sanjay@123456"
            )
        ).thenReturn(User(name = "Sanjay", email = "sanjay@gmail.com", password = "123456"))
        vm.proceedToLogin(
            User(
                name = "Sanjay",
                email = "sanjay@gmail.com",
                password = "Sanjay@123456"
            )
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val result = vm.user.getOrAwaitValue()
        assertEquals("Sanjay", result.name)
    }

    @Test
    fun validateLoginWithInvalidCredential_expectedNull() = runTest {
        Mockito.`when`(
            repository.getUserByEmailAndPassword(
                email = "sanjay@gmail.com",
                password = "Sanjay@123456"
            )
        ).thenReturn(User(name = "Sanjay", email = "sanjay", password = "Sanjay@123456"))
        vm.proceedToLogin(User(name = "Sanjay", email = "sanjay", password = "Sanjay@123456"))
        testDispatcher.scheduler.advanceUntilIdle()
        val result = vm.user.getOrAwaitValue()
        assertNull(result.name)
    }

    @Test
    fun validateOnEmailEditTextChanged_expectedTrue() {
        vm.onEmailEditTextChanged("", 0, 0, 0)
        val result = vm.validEmail.getOrAwaitValue()
        assertTrue(result)
    }

    @Test
    fun validateOnPasswordEditTextChanged_expectedTrue() {
        vm.onEmailEditTextChanged("", 0, 0, 0)
        val result = vm.validPassword.getOrAwaitValue()
        assertTrue(result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}