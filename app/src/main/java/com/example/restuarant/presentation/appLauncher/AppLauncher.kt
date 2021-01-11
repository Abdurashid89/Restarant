package com.example.restuarant.presentation.appLauncher

import com.example.restuarant.Screens
import com.example.restuarant.model.interactor.LaunchIntegrator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val launchIntegrator: LaunchIntegrator,
    private val router: Router
) {
    fun onLunch() {
        launchIntegrator.signInToLastSession()
    }

    fun coldStart() {
        val rootScreen = Screens.MainFlow
        router.newRootScreen(rootScreen)
    }

}