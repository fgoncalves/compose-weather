package com.example.androiddevchallenge.ui.viewmodels.intents

import androidx.annotation.StringRes
import com.example.androiddevchallenge.domain.entities.HourlyForecast

sealed class Intent

object GetHourlyForecastIntent : Intent()

data class GotHourlyForecastIntent(
    val forecast: HourlyForecast,
) : Intent()

data class ErrorGettingHourlyForecastIntent(
    @StringRes
    val title: Int,
    @StringRes
    val msg: Int,
) : Intent()