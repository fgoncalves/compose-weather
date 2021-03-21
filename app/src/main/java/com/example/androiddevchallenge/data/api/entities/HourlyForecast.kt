package com.example.androiddevchallenge.data.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourlyForecast(
    @Json(name = "lat")
    val lat: Float,
    @Json(name = "lon")
    val lon: Float,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int,
    @Json(name = "current")
    val current: Forecast,
    @Json(name = "hourly")
    val hourly: List<Forecast> = emptyList(),
)