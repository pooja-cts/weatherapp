package com.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Double? = 0.0,
    @SerializedName("feelsLike")
    val feelsLike: Double? = 0.0,
    @SerializedName("tempMin")
    val tempMin: Double? = 0.0,
    @SerializedName("tempMax")
    val tempMax: Double? = 0.0,
    @SerializedName("pressure")
    val pressure: Int? = 0,
    @SerializedName("humidity")
    val humidity: Int? = 0
)