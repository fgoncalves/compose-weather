package com.example.androiddevchallenge.domain.entities

data class HourlyForecast(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val current: Forecast,
    val hourly: List<Forecast> = emptyList(),
)