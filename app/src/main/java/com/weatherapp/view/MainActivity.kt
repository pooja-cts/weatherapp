package com.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.weatherapp.Constants.COUNTRY_CODE
import com.weatherapp.NetworkStateReceiver
import com.weatherapp.R
import com.weatherapp.SharedPreferenceUtils
import com.weatherapp.Utils.formatTime
import com.weatherapp.Utils.kelvinToCelsius
import com.weatherapp.Utils.kelvinToFahrenheit
import com.weatherapp.model.Weather.WeatherResponse
import com.weatherapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateChangeListener {

    lateinit var context: Context

    lateinit var mainActivityViewModel: MainActivityViewModel

    lateinit var networkStateReceiver: NetworkStateReceiver

    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity

        // Initialize and register the NetworkStateReceiver
        networkStateReceiver = NetworkStateReceiver(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStateReceiver, intentFilter)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        requestForLocation()
        fetchLastSearchedLocation()
        handleButtonEvents()
        handlingAutoCompleteEditTextWithLocation()
    }

    private fun handlingAutoCompleteEditTextWithLocation() {
        // Adding Text Watcher to Search Cities as User Enters City Name
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                // Called the GEO API, and observing the Live Data of List of Cities
                val cityAdapter =
                    ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line)
                mainActivityViewModel.getCitiesListInfo(s.toString() + "," + COUNTRY_CODE)
                    .observe(context as MainActivity,
                        Observer { cities ->
                            cityAdapter.clear()
                            if (cities != null) {
                                for (city in cities) {
                                    cityAdapter.add(city.cityName + ", " + city.state)
                                    autoCompleteTextView.setAdapter(cityAdapter)
                                }
                            }
                        })
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun handleButtonEvents() {
        // Button to Clear City Name Field
        btnClear.setOnClickListener {
            autoCompleteTextView.setText("")
        }

        //If location is granted and button is visible user can view their current location's weather
        btnCurrentLocation.setOnClickListener {
            autoCompleteTextView.setText("")
            getLocation()
        }

        // Button to Perform Search for Weather of the entered city
        btnClick.setOnClickListener {
            getWeather()
        }

    }

    private fun fetchLastSearchedLocation() {
        // Fetching Last Searched Location's Weather
        SharedPreferenceUtils.getString(context).let {
            if (!it.isEmpty()) {
                autoCompleteTextView.setText(it)
                getWeather()
            }
        }
    }

    private fun requestForLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            btnCurrentLocation.visibility = View.GONE
        } else {
            // Permission is already granted, proceed with getting the location
            //Default Showing Last City Searched by the User
            btnCurrentLocation.visibility = View.VISIBLE
        }
    }


    // Seperate Function For Fetching Data From Location
    private fun getWeatherInfoLatLong(
        lat: String, lonng: String
    ) {
        //Showing Loader when API is being called
        progressBar.visibility = View.VISIBLE

        // Called the Weather API, and observing the Live Data
        mainActivityViewModel.getWeatherInfoLatLong(
            lat, lonng
        )!!
            .observe(this, Observer {
                if (it != null) {
                    setData(it)
                }
                progressBar.visibility = View.GONE
            })
    }

    private fun setData(it: WeatherResponse) {
        //Setting Data to the UI Screen
        SharedPreferenceUtils.saveString(
            context,
            it.name.toString()
        )

        cityNametv.text = it.name

        val temperatureInCelsuis =
            it.main?.temp?.let { it1 -> kelvinToCelsius(it1) }
                .toString()

        val temperatureInFahrenheit =
            it.main?.temp?.let { it1 -> kelvinToFahrenheit(it1) }
                .toString()

        temperatureAndWeathertv.text =
            "$temperatureInCelsuis ℃ | $temperatureInFahrenheit F \n" + it.weather?.get(
                0
            )?.description.toString()

        sunsettime.setText(it.sys?.sunset?.let { it1 -> formatTime(it1.toLong()) })
        sunrisetime.setText(it.sys?.sunrise?.let { it2 -> formatTime(it2.toLong()) })
    }


    //Function to get weather info according to the user entered data
    private fun getWeather(
    ) {
        //Showing Loader when API is being called
        progressBar.visibility = View.VISIBLE

        // Called the Weather API, and observing the Live Data

        mainActivityViewModel.getWeatherInfo(
            autoCompleteTextView.text.toString()
        )!!
            .observe(this, Observer {
                if (it != null) {
                    setData(it)
                }
                progressBar.visibility = View.GONE
            })

    }


    override fun onNetworkAvailable() {
        // Showing Main Screen and Hiding No Connection Text
        mainLayout.visibility = View.VISIBLE
        noInternetConnection.visibility = View.GONE
    }

    override fun onNetworkUnavailable() {
        // Showing No Connection Text and Hiding Main Screen
        mainLayout.visibility = View.GONE
        noInternetConnection.visibility = View.VISIBLE

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with getting the location
                getLocation()
                btnCurrentLocation.visibility = View.VISIBLE

            } else {
                // Permission is denied
                Toast.makeText(this, "Location permission is not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationProvider = LocationManager.GPS_PROVIDER

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, handle it accordingly
            return
        }

        val location = locationManager.getLastKnownLocation(locationProvider)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            getWeatherInfoLatLong(latitude.toString(), longitude.toString())
            // Printing the latitude and longitude
            Log.d("Location", "Latitude: $latitude, Longitude: $longitude")


        } else {
            // Location is not available
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the NetworkStateReceiver
        unregisterReceiver(networkStateReceiver)
    }
}
