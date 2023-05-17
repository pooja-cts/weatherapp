package com.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.Constants.RESULT_LIMIT
import com.weatherapp.model.Location.City
import com.weatherapp.model.Weather.WeatherResponse
import com.weatherapp.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<WeatherResponse>? = null
    var list: MutableLiveData<List<City>> = MutableLiveData()

    fun getWeatherInfo(
        query: String
    ): LiveData<WeatherResponse>? {
        servicesLiveData = MainActivityRepository.getWeatherData(query)
        return servicesLiveData
    }

    fun getWeatherInfoLatLong(
        lat: String,lonng: String
    ): LiveData<WeatherResponse>? {
        servicesLiveData = MainActivityRepository.getWeatherDataWithLatLong(lat,lonng)
        return servicesLiveData
    }

    fun getCitiesListInfo(query: String): MutableLiveData<List<City>> {
        list = MainActivityRepository.getCities(query, RESULT_LIMIT)
        return list
    }
}