package com.example.androiddevchallenge.data.api

import com.example.androiddevchallenge.commons.ONECALL_PATH
import com.example.androiddevchallenge.data.api.entities.HourlyForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(ONECALL_PATH)
    suspend fun get(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String = "minutely,daily"
    ): HourlyForecast
}