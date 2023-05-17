package com.weatherapp

import com.weatherapp.model.Weather.Sys
import org.junit.Assert.assertEquals
import org.junit.Test

class SysTest {

    @Test
    fun `test Sys object creation with valid values`() {
        val sys = Sys(
            type = 1,
            id = 123,
            country = "US",
            sunrise = 124545,
            sunset = 174645
        )

        assertEquals(1, sys.type)
        assertEquals(123, sys.id)
        assertEquals("US", sys.country)
        assertEquals(124545, sys.sunrise)
        assertEquals(174645, sys.sunset)
    }

    @Test
    fun `test Sys object creation with default values`() {
        val sys = Sys()

        assertEquals(0, sys.type)
        assertEquals(0, sys.id)
        assertEquals("", sys.country)
        assertEquals(0, sys.sunrise)
        assertEquals(0, sys.sunset)
    }
}
