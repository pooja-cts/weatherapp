package com.weatherapp

import com.weatherapp.model.Location.City
import org.junit.Assert
import org.junit.Test

class CityTest {

    @Test
    fun `test City object creation with valid values`() {
        val city = City(
            cityName = "Newark",
            state = "New Jersey"
        )

        Assert.assertEquals("Newark", city.cityName)
        Assert.assertEquals("New Jersey", city.state)

    }

    @Test
    fun `test Sys object creation with default values`() {
        val city = City()

        Assert.assertEquals("", city.state)
        Assert.assertEquals("", city.cityName)

    }

}