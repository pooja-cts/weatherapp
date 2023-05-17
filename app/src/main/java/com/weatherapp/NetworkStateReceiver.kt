package com.weatherapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkStateReceiver(private val listener: NetworkStateChangeListener) : BroadcastReceiver() {

    // BroadcastReceiver function to check Internet Connectivity
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            listener.onNetworkAvailable()
        } else {
            listener.onNetworkUnavailable()
        }
    }

    // Created two function according to possible scenarios.
    interface NetworkStateChangeListener {
        fun onNetworkAvailable()
        fun onNetworkUnavailable()
    }
}