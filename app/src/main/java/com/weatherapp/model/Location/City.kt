package com.weatherapp.model.Location

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("name")
    val cityName: String = "",
    @SerializedName("state")
    val state: String = ""
)