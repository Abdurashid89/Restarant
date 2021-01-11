package com.example.restuarant.di.module

import com.example.restuarant.model.system.pull.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class FlowNavigationModule(globalRouter: Router) : Module() {
    init {
        val cicerone = Cicerone.create(FlowRouter(globalRouter))
        bind(FlowRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }

}