package com.example.androiddevchallenge.ui.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class TemperatureUnit {
    KELVIN,
    CELSIUS,
    FAHRENHEIT,
}

data class HourForecast(
    val temperature: String,
    val time: String,
)

data class DayForecast(
    val description: String,
    val avgTemp: String,
    val realFeel: String,
    val maxTemp: String,
    val minTemp: String,
    @DrawableRes
    val background: Int,
    val hourlyForecast: List<HourForecast> = emptyList(),
)

data class WeatherForecast(
    val locationName: String,
    val currentDay: Int,
    val dailyForecast: List<DayForecast> = emptyList(),
)

sealed class RequestState {
    object Loading : RequestState()
    object Loaded : RequestState()

    data class Error(
        @StringRes
        val title: Int,
        @StringRes
        val errorMessage: Int,
    ) : RequestState()
}

data class AppState(
    val weatherForecast: WeatherForecast,
    val tempUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val hourFormatPatter: String = "HH:mm",
    val requestState: RequestState,
)