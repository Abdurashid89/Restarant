package com.example.restuarant.app

import android.app.Application
import androidx.multidex.BuildConfig
import com.example.restuarant.di.DI
import com.example.restuarant.di.module.AppModule
import com.example.restuarant.extentions.currentTimeToLong
import timber.log.Timber
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
        initLogger()
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

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance: App
    }
}