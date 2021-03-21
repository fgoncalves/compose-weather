/**
 * I don't think anyone is proud of what's happening here. Let's just accept it and move on...
 */

package com.example.androiddevchallenge.ui.state

import androidx.annotation.VisibleForTesting
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
        hourlyForecast = hourlyBreakdown.map { it.toHourForecast(format, unit) }
    )

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