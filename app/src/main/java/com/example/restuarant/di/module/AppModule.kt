package com.example.restuarant.di.module

import android.content.Context
import com.example.restuarant.di.provider.MoshiProvider
import com.example.restuarant.model.storage.Prefs
import com.squareup.moshi.Moshi
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
//        bind(Sco::class.java).toInstance(context)
        bind(Prefs::class.java).singleton()
        bind(Moshi::class.java).toProvider(MoshiProvider::class.java).providesSingleton()
//        bind(ResourceManager::class.java).singleton()
//        bind(SystemMessageNotifier::class.java).toInstance(SystemMessageNotifier())

        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}