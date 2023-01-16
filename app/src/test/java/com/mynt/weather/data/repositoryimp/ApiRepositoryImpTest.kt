package com.mynt.weather.data.repositoryimp

import com.mynt.weather.OpenWeatherResponseHelper
import com.mynt.weather.data.api.OpenWeatherApi
import com.mynt.weather.data.models.ErrorResponse
import com.mynt.weather.data.models.OpenWeatherResponse
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRepositoryImpTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var openWeatherApi: OpenWeatherApi
    lateinit var repository: ApiRepositoryImp

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        openWeatherApi =
            Retrofit.Builder().baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi::class.java)

        repository = ApiRepositoryImp(openWeatherApi)
    }

    @Test
    fun testGetWeatherDetail_expectedOpenWeatherResponse() = runTest {
        val mockResponse = MockResponse()
        val bodyJson = OpenWeatherResponseHelper.readJsonResourceFromFile("/weather_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(bodyJson)
        mockWebServer.enqueue(mockResponse)

        val response = repository.getWeatherDetail("weather")
        mockWebServer.takeRequest()

        when (response) {
            is AppResponse.Success -> {
                if (response.data is OpenWeatherResponse) {
                    assertNotNull(response.data)
                    assertEquals(200, (response.data as OpenWeatherResponse).cod)
                }
            }
            else -> {
                assertFalse(true)
            }
        }
    }

    @Test
    fun testGetWeatherDetail_expectedErrorResponse() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)
        mockResponse.setBody(
            ErrorResponse(
                cod = 401,
                message = Constants.somethingWentWrong
            ).toString()
        )
        mockWebServer.enqueue(mockResponse)

        val response = repository.getWeatherDetail("weather")
        mockWebServer.takeRequest()

        when (response) {
            is AppResponse.Error -> {
                assertEquals(Constants.somethingWentWrong, response.message)
            }
            else -> {
                assertFalse(true)
            }
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}