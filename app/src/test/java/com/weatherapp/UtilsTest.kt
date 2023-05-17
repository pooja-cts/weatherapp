package com.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun testFormatTime() {
        val timeInMillis = 1621317600000L // May 18, 2021 02:00:00 AM UTC
        val expectedTime = "02:00 AM"

        val formattedTime = Utils.formatTime(timeInMillis)

        assertEquals(expectedTime, formattedTime)
    }

    @Test
    fun testKelvinToCelsius() {
        val kelvin = 300.0
        val expectedCelsius = "27.00"

        val convertedCelsius = Utils.kelvinToCelsius(kelvin)

        assertEquals(expectedCelsius, convertedCelsius)
    }

    @Test
    fun testKelvinToFahrenheit() {
        val kelvin = 300.0
        val expectedFahrenheit = "80.33"

        val convertedFahrenheit = Utils.kelvinToFahrenheit(kelvin)

        assertEquals(expectedFahrenheit, convertedFahrenheit)
    }
}