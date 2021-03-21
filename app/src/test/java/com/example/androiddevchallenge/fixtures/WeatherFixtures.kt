package com.example.androiddevchallenge.fixtures

import com.example.androiddevchallenge.data.api.entities.HourlyForecast
import com.squareup.moshi.Moshi

object WeatherFixtures {
    fun hourlyForecast() =
        javaClass.classLoader!!
            .getResourceAsStream("hourly_forecast.json")!!
            .bufferedReader()
            .use {
                Moshi.Builder()
                    .build()
                    .adapter(HourlyForecast::class.java)
                    .fromJson(it.readText())
            }!!
}
