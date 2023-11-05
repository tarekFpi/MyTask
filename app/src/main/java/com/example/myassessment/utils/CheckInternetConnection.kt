package com.example.myassessment.utils

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


class CheckInternetConnection @Inject constructor(){

    fun isInternetAvailable(context: Context): Boolean {
        val activeNetworkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}