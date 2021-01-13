package com.example.restuarant.app

import android.app.Application
import com.example.restuarant.di.DI
import com.example.restuarant.di.module.AppModule
import com.github.moxy_community.moxy.androidx.BuildConfig
import toothpick.Toothpick
import toothpick.configuration.Configuration

/**
 * Created by shohboz on 11,Январь,2021
 */
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope()
        instance = this
    }
    private fun initAppScope() {
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    companion object {
        lateinit var instance: App
    }
}