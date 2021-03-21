package com.example.androiddevchallenge.domain.entities

import com.example.androiddevchallenge.commons.Epoch
import com.example.androiddevchallenge.commons.Kelvin

data class Forecast(
    val time: Epoch,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Kelvin,
    val feelsLike: Float,
    val pressure: Float,
    val humidity: Float,
    val dewPoint: Kelvin,
    val uvIndex: Float,
    val clouds: Float,
    val visibility: Float,
    val windSpeed: Float,
    val windDeg: Int,
    val windGust: Float? = null,
    val weather: List<WeatherBit> = emptyList(),
)