package com.example.restuarant.model.storage

import android.content.Context
import javax.inject.Inject

/**
 * Created by shohboz on 11,Январь,2021
 */
class Prefs @Inject constructor(
    private val context: Context
){
    private fun getSharedPreference(prefName: String) =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    private val APP_NAME = "app_name"

    private val localStorage by lazy { getSharedPreference(APP_NAME) }

    var account: Boolean
        get() = localStorage.getBoolean("ACCOUNT", false)
        set(value) = localStorage.edit().putBoolean("ACCOUNT", value).apply()

    var accessToken: String?
        get() = localStorage.getString("TOKEN","")
        set(value) = localStorage.edit().putString("TOKEN", value).apply()



    //
}