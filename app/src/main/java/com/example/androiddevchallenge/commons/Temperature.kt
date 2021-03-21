package com.example.androiddevchallenge.commons

import java.util.Locale

sealed class Temperature

data class Kelvin(
    val value: Float,
) : Temperature()

data class Celsius(
    val value: Float,
) : Temperature()

data class Fahrenheit(
    val value: Float,
) : Temperature()

val Float.K get() = Kelvin(this)
val Float.F get() = Fahrenheit(this)
val Float.C get() = Celsius(this)

fun Kelvin.toCelsius() = (value - 273.15f).C
fun Kelvin.toFahrenheit() = ((value - 273.15f) * 9 / 5 + 32).F

fun Temperature.format() =
    when (this) {
        is Kelvin -> "%.0f K".format(Locale.ENGLISH, value)
        is Celsius -> "%.0f ºC".format(Locale.ENGLISH, value)
        is Fahrenheit -> "%.0f F".format(Locale.ENGLISH, value)
    }
