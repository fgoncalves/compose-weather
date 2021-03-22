/**
 * I don't think anyone is proud of what's happening here. Let's just accept it and move on...
 */

package com.example.androiddevchallenge.ui.state

import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.commons.C
import com.example.androiddevchallenge.commons.F
import com.example.androiddevchallenge.commons.K
import com.example.androiddevchallenge.commons.Kelvin
import com.example.androiddevchallenge.commons.Temperature
import com.example.androiddevchallenge.commons.format
import com.example.androiddevchallenge.commons.toCelsius
import com.example.androiddevchallenge.commons.toDateTime
import com.example.androiddevchallenge.commons.toFahrenheit
import com.example.androiddevchallenge.domain.entities.Forecast
import com.example.androiddevchallenge.domain.entities.HourlyForecast
import kotlin.math.max
import kotlin.math.min
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

fun HourlyForecast.toAppState(
    hourFormatPatter: String = "HH:mm",
    unit: TemperatureUnit = TemperatureUnit.KELVIN,
): AppState {
    val perDay = groupForecastsByDays()
    val formatter = DateTimeFormat.forPattern(hourFormatPatter)

    val dailyForecast = listOf(
        current.toDayForecast(
            perDay.forDay(current.time.toDateTime().withTimeAtStartOfDay()) ?: emptyList(),
            formatter,
            unit,
        )
    ) + perDay.map {
        it[0].toDayForecast(
            perDay.forDay(it[0].time.toDateTime().withTimeAtStartOfDay()) ?: emptyList(),
            formatter,
            unit,
        )
    }

    return AppState(
        requestState = RequestState.Loaded,
        weatherForecast = WeatherForecast(
            "Lisbon",
            0,
            dailyForecast,
        )
    )
}

private fun Forecast.toDayForecast(
    hourlyBreakdown: List<Forecast>,
    format: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm"),
    unit: TemperatureUnit = TemperatureUnit.KELVIN
) =
    DayForecast(
        description = weather[0].description,
        avgTemp = temp.toUnit(unit).format(),
        realFeel = feelsLike.asTemperature(unit).format(),
        maxTemp = hourlyBreakdown.maxTemp().toUnit(unit).format(),
        minTemp = hourlyBreakdown.minTemp().toUnit(unit).format(),
        background = backgroundResource(),
        hourlyForecast = hourlyBreakdown.map { it.toHourForecast(format, unit) }
    )

@DrawableRes
private fun Forecast.backgroundResource() =
    when (weather[0].icon) {
        "01d" -> R.drawable.clear_skies_day
        "01n" -> R.drawable.clear_skies_night

        "02d" -> R.drawable.few_clouds_day
        "02n" -> R.drawable.few_clouds_night

        "03d", "03n", "04d", "04n" -> R.drawable.scattered_clouds

        "09d", "09n" -> R.drawable.shower_rain

        "10d", "10n" -> R.drawable.rain

        "11d", "11n" -> R.drawable.thunderstorms

        "13d", "13n" -> R.drawable.snow

        "50d", "50n" -> R.drawable.mist

        else -> R.drawable.clear_skies_day
    }

private fun Forecast.toHourForecast(
    format: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm"),
    unit: TemperatureUnit,
) =
    HourForecast(
        temp.toUnit(unit).format(),
        time.toDateTime().toString(format),
    )

private fun Kelvin.toUnit(unit: TemperatureUnit): Temperature =
    when (unit) {
        TemperatureUnit.CELSIUS -> toCelsius()
        TemperatureUnit.FAHRENHEIT -> toFahrenheit()
        else -> this
    }

private fun Float.asTemperature(unit: TemperatureUnit = TemperatureUnit.KELVIN) =
    when (unit) {
        TemperatureUnit.KELVIN -> K
        TemperatureUnit.CELSIUS -> C
        TemperatureUnit.FAHRENHEIT -> F
    }

@VisibleForTesting
fun HourlyForecast.groupForecastsByDays(): List<List<Forecast>> =
    hourly.groupBy {
        it.time.toDateTime()
            .withTimeAtStartOfDay()
    }.toSortedMap()
        .values
        .toList()

private fun List<List<Forecast>>.forDay(day: DateTime) =
    find {
        it[0].time.toDateTime()
            .withTimeAtStartOfDay() == day
    }

private fun List<Forecast>.maxTemp() =
    fold(Float.MIN_VALUE.K) { acc, elem ->
        max(acc.value, elem.temp.value).K
    }

private fun List<Forecast>.minTemp() =
    fold(Float.MAX_VALUE.K) { acc, elem ->
        min(acc.value, elem.temp.value).K
    }