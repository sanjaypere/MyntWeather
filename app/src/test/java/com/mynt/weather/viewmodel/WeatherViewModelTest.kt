package com.mynt.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.data.db.entity.WeatherEntity
import com.mynt.weather.data.models.OpenWeatherResponse
import com.mynt.weather.data.repositoryimp.ApiRepositoryImp
import com.mynt.weather.data.repositoryimp.WeatherRepositoryImp
import com.mynt.weather.getOrAwaitValue
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repository: ApiRepositoryImp

    @Mock
    lateinit var userEntity: UserEntity

    @Mock
    lateinit var weatherRepositoryImp: WeatherRepositoryImp
    lateinit var vm: WeatherViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        vm = WeatherViewModel(repository, weatherRepositoryImp)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetWeatherDetail_expectedOpenWeatherResponse() = runTest {
        Mockito.`when`(userEntity.id)
            .thenReturn(1)
        Mockito.`when`(repository.getWeatherDetail("weather"))
            .thenReturn(AppResponse.Success(data = OpenWeatherResponse(cod = 200)))
        Mockito.`when`(weatherRepositoryImp.insertWeather(WeatherEntity()))
            .thenReturn(null)

        vm.getWeatherDetail("weather")

        testDispatcher.scheduler.advanceUntilIdle()
        val result = vm.weatherResponse.getOrAwaitValue()
        val appResponse = vm.appResponse.getOrAwaitValue()

        Assert.assertEquals(200, result.cod)
        Assert.assertTrue(appResponse is AppResponse.Success)
    }

    @Test
    fun testGetWeatherDetail_expectedErrorResponse() = runTest {
        Mockito.`when`(userEntity.id)
            .thenReturn(1)
        Mockito.`when`(repository.getWeatherDetail("weather"))
            .thenReturn(
                AppResponse.Error(
                    message = Constants.somethingWentWrong
                )
            )
        Mockito.`when`(weatherRepositoryImp.insertWeather(WeatherEntity()))
            .thenReturn(null)

        vm.getWeatherDetail("weather")
        testDispatcher.scheduler.advanceUntilIdle()
        val appResponse = vm.appResponse.getOrAwaitValue()

        Assert.assertTrue(appResponse is AppResponse.Error)
    }

    @Test
    fun testUpdateNetworkState_expectedTrue() = runTest {
        vm.updateNetworkState(true)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = vm.isNetworkAvailable.getOrAwaitValue()
        Assert.assertTrue(result)
    }

    @Test
    fun testSetUserEntity_expectedUser() = runTest {
        val user = UserEntity(id = 1, name = "Sanjay", email = "san@pere.com", password = "1234")
        vm.setUserEntity(user)
        val result = vm.user
        Assert.assertEquals("Sanjay", result?.name ?: "")
    }
}