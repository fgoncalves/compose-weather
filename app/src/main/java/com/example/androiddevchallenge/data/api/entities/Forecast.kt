package com.example.androiddevchallenge.data.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "sunrise")
    val sunrise: Long? = null,
    @Json(name = "sunset")
    val sunset: Long? = null,
    @Json(name = "temp")
    val temp: Float,
    @Json(name = "feels_like")
    val feelsLike: Float,
    @Json(name = "pressure")
    val pressure: Float,
    @Json(name = "humidity")
    val humidity: Float,
    @Json(name = "dew_point")
    val dewPoint: Float,
    @Json(name = "uvi")
    val uvIndex: Float,
    @Json(name = "clouds")
    val clouds: Float,
    @Json(name = "visibility")
    val visibility: Float,
    @Json(name = "wind_speed")
    val windSpeed: Float,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "wind_gust")
    val windGust: Float? = null,
    @Json(name = "weather")
    val weather: List<WeatherBit> = emptyList(),
)