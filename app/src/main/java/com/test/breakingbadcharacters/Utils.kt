package com.test.breakingbadcharacters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Utility class contain mostly required functions
 */
object Utils {


    const val INTENT_CHARACTER_KEY = "character"

    /**function to print the log on logcat, it will not print log when build type is debug*/
    fun logDisplay(TAG: String, message: String) {
        if (BuildConfig.BUILD_TYPE == "debug") {
            Log.d(TAG, message)
        }
    }

    /**function to check internet connection via wifi, mobile data and bluetooth*/
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    /** Show keyBoard */
    fun showKeyBoard(view: View, isShow: Boolean){
        val imm: InputMethodManager? =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if(isShow) {
            view.requestFocus()
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }else{
            view.clearFocus()
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
