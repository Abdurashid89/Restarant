package com.example.restuarant.presentation.waiter

import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class WaiterPresenter @Inject constructor(
    private val router: FlowRouter,
    private val prefs:Prefs
) :BasePresenter<WaiterView>(){

    fun onBackPressed(){
        router.exit()
    }

    fun openClientDialog(){
        viewState.openClientCountDialog()
    }

}