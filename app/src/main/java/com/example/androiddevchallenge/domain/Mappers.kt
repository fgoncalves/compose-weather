package com.example.androiddevchallenge.domain

import com.example.androiddevchallenge.commons.K
import com.example.androiddevchallenge.commons.epochMs
import com.example.androiddevchallenge.domain.entities.Forecast
import com.example.androiddevchallenge.domain.entities.HourlyForecast
import com.example.androiddevchallenge.domain.entities.WeatherBit
import com.example.androiddevchallenge.data.api.entities.Forecast as ApiForecast
import com.example.androiddevchallenge.data.api.entities.HourlyForecast as ApiHourlyForecast
import com.example.androiddevchallenge.data.api.entities.WeatherBit as ApiWeatherBit

fun ApiWeatherBit.toDomainEntity() =
    WeatherBit(
        id,
        main,
        description,
        icon,
    )

fun ApiForecast.toDomainEntity(timezoneOffsetMillis: Int) =
    Forecast(
        (dt * 1000).epochMs(timezoneOffsetMillis),
        sunrise,
        sunset,
        temp.K,
        feelsLike,
        pressure,
        humidity,
        dewPoint.K,
        uvIndex,
        clouds,
        visibility,
        windSpeed,
        windDeg,
        windGust,
        weather.map(ApiWeatherBit::toDomainEntity),
    )

fun ApiHourlyForecast.toDomainEntity() =
    HourlyForecast(
        lat,
        lon,
        timezone,
        current.toDomainEntity(timezoneOffset * 100),
        hourly.map { it.toDomainEntity(timezoneOffset * 100) },
    )