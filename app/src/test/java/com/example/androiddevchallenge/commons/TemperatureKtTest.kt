package com.example.androiddevchallenge.commons

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TemperatureKtTest {
    @Test
    fun `should convert to celsius`() {
        assertThat(273.15f.K.toCelsius().value).isWithin(1.0E-2f).of(0f)
        assertThat(789f.K.toCelsius().value).isWithin(1.0E-2f).of(515.85f)
        assertThat(0f.K.toCelsius().value).isWithin(1.0E-2f).of((-273.15f))
    }

    @Test
    fun `should convert to Fahrenheit`() {
        assertThat(255.372f.K.toFahrenheit().value).isWithin(1.0E-2f).of(0f)
        assertThat(789f.K.toFahrenheit().value).isWithin(1.0E-2f).of(960.53f)
        assertThat(0f.K.toFahrenheit().value).isWithin(1.0E-2f).of((-459.67f))
    }

    @Test
    fun `formats temperature correctly`() {
        assertThat(123f.K.format()).isEqualTo("123 K")
        assertThat(123f.F.format()).isEqualTo("123 F")
        assertThat(123f.C.format()).isEqualTo("123 ºC")
    }
}