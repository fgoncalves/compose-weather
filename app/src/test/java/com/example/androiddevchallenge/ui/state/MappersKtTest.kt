package com.example.androiddevchallenge.ui.state

import com.example.androiddevchallenge.domain.toDomainEntity
import com.example.androiddevchallenge.fixtures.WeatherFixtures
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MappersKtTest {
    private val forecast = WeatherFixtures.hourlyForecast().toDomainEntity()

    @Test
    fun `groupForecastsByDays should return sorted list of forecasts per day`() {
        val result = forecast.groupForecastsByDays()

        assertThat(result[0]).containsExactly(
            *(forecast.hourly.slice(0..8).toTypedArray())
        )
        assertThat(result[1]).containsExactly(
            *(forecast.hourly.slice(9..32).toTypedArray())
        )
        assertThat(result[2]).containsExactly(
            *(forecast.hourly.slice(33..47).toTypedArray())
        )
    }
}