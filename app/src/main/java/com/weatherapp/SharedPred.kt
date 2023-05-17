package com.weatherapp

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtils {
    private const val PREF_NAME = "WeatherAppPreferences"
    private const val PREF_CITY_NAME = "CityName"

    // Shared Preference To Store Last Searched City By the User
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Saving City Name to Shared Preference To Store Last Searched City By the User
    fun saveString(context: Context, value: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(PREF_CITY_NAME, value)
        editor.apply()
    }

    // Fetching City Name from Shared Preference To Store Last Searched City By the User
    fun getString(context: Context): String {
        return getSharedPreferences(context).getString(PREF_CITY_NAME, "") ?: ""
    }

}