package com.weatherapp

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun formatTime(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }


    fun kelvinToCelsius(kelvin: Double): String {
        // Function to Convert Kelvin to Celsius
        val d = kelvin - 273

        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(d)
    }

    fun kelvinToFahrenheit(kelvin: Double): String {
        // Function to Convert Kelvin to Fahrenheit
        val d = (kelvin - 273.15) * 9 / 5 + 32

        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(d)
    }
}