package com.example.androiddevchallenge.ui.viewmodels

import com.example.androiddevchallenge.domain.usecases.GetHourlyForecastUseCase
import com.example.androiddevchallenge.rules.CoroutineTestRule
import com.example.androiddevchallenge.ui.state.AppState
import com.example.androiddevchallenge.ui.state.RequestState
import com.example.androiddevchallenge.ui.state.WeatherForecast
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val useCase = mock<GetHourlyForecastUseCase>()

    @Test
    fun `initialState provides empty state`() = coroutineTestRule.runBlockingTest {
        assertThat(
            MainViewModel(useCase).state
                .first()
        ).isEqualTo(
            AppState(
                weatherForecast = WeatherForecast(
                    locationName = "",
                    currentDay = 0,
                ),
                requestState = RequestState.Loading,
            )
        )
    }
}
