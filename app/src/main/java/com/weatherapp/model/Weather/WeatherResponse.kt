package com.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("coord")
    val coord: Coord? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("base")
    val base: String? = "",
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("visibility")
    val visibility: Int? = 0,
    @SerializedName("wind")
    val wind: Wind? = null,
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("dt")
    val dt: Int? = 0,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("timezone")
    val timezone: Int? = 0,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("cod")
    val cod: Int? = 0
)