package com.example.androiddevchallenge.domain.usecases

import com.example.androiddevchallenge.data.api.WeatherApi
import com.example.androiddevchallenge.data.api.entities.Forecast
import com.example.androiddevchallenge.data.api.entities.HourlyForecast
import com.example.androiddevchallenge.rules.CoroutineTestRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetHourlyForecastUseCaseImplTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val apiWeatherApi = mock<WeatherApi> {
        onBlocking {
            get(any(), any(), any())
        } doReturn HourlyForecast(
            0f,
            12f,
            "foo",
            0,
            Forecast(
                dt = 0,
                temp = 0f,
                feelsLike = 1f,
                pressure = 1f,
                humidity = 123f,
                dewPoint = 123f,
                uvIndex = 345f,
                clouds = 123f,
                visibility = 345f,
                windSpeed = 234f,
                windDeg = 4,
            ),
        )
    }
    private val useCase = GetHourlyForecastUseCaseImpl(apiWeatherApi)

    @Test
    fun `calls api with lat and lon`() = coroutineTestRule.runBlockingTest {
        useCase.get(123f, 456f)

        verifyBlocking(apiWeatherApi) {
            apiWeatherApi.get(123f, 456f)
        }
    }
}