package com.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.weatherapp.Constants.API_KEY
import com.weatherapp.model.Location.City
import com.weatherapp.model.Weather.WeatherResponse
import com.weatherapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceSetterGetter = MutableLiveData<WeatherResponse>()
    val list: MutableLiveData<List<City>> = MutableLiveData()

    //Calling the API for fetching weather info using search query and handling success and failure cases
    fun getWeatherData(
        query: String
    ): MutableLiveData<WeatherResponse> {

        val call = RetrofitClient.apiInterface.getWeather(
            query, API_KEY
        )
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("API Error", "Failed to make API call: " + t.message)
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                val data = response.body()
                serviceSetterGetter.value = data
            }
        })

        return serviceSetterGetter
    }

    //Calling the API for fetching weather info using lat and long and handling success and failure cases
    fun getWeatherDataWithLatLong(
        lat: String, lonng: String
    ): MutableLiveData<WeatherResponse> {

        val call = RetrofitClient.apiInterface.getWeatherLatLong(
            API_KEY, lat, lonng
        )
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("API Error", "Failed to make API call: " + t.message)
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                val data = response.body()
                serviceSetterGetter.value = data
            }
        })

        return serviceSetterGetter
    }


    //Calling the API for getting cities list and handling success and failure cases
    fun getCities(query: String, limit: Int): MutableLiveData<List<City>> {
        val call: Call<List<City>> = RetrofitClient.apiInterface.getLocation(query, limit, API_KEY)
        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.isSuccessful) {
                    val cities: List<City>? = response.body()
                    if (cities != null && cities.isNotEmpty()) {
                        list.value = cities
                    } else {
                        Log.e("API Error", "Response Code: " + response.code())
                    }
                } else {
                    Log.e("API Error", "Response Error Code: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                Log.e("API Error", "Failed to make API call: " + t.message)
            }
        })
        return list
    }
}