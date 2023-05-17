package com.weatherapp.retrofit

import com.weatherapp.model.Location.City
import com.weatherapp.model.Weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // API for weather info with search query
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") location: String?,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    // API for weather info using Lat and Long
    @GET("data/2.5/weather")
    fun getWeatherLatLong(
        @Query("appid") apiKey: String,
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): Call<WeatherResponse>


    // API for location and fetching cities and state names
    @GET("geo/1.0/direct")
    fun getLocation(
        @Query("q") location: String?,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): Call<List<City>>

}
