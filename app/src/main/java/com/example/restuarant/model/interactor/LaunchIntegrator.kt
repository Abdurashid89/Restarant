package com.example.restuarant.model.interactor

import com.example.restuarant.di.DI
import com.example.restuarant.di.module.ServerModule
import toothpick.Toothpick
import javax.inject.Inject

class LaunchIntegrator @Inject constructor() {

    fun signInToLastSession() {
        if (!Toothpick.isScopeOpen(DI.SERVER_SCOPE)) {
            Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
                .installModules(ServerModule())
        }
    }
}