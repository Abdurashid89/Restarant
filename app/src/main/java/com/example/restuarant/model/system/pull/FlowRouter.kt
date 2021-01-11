package com.example.restuarant.model.system.pull

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FlowRouter(private val appRouter: Router) : Router() {
    fun startFlow(screen: SupportAppScreen) {
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: SupportAppScreen) {
        appRouter.newRootChain(screen)
    }

    fun finishFlow() {
        appRouter.exit()
    }
}