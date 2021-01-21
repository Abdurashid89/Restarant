package com.example.restuarant.presentation.waiter

import com.example.restuarant.model.interactor.WaiterInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class WaiterPresenter @Inject constructor(
    private val router: FlowRouter,
    private val prefs:Prefs,
    private val interactor: WaiterInteractor
) :BasePresenter<WaiterView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

    }

    fun openClientDialog(){
        viewState.openClientCountDialog()
    }

    fun onBackPressed(){
        router.exit()
    }

    fun getMenu(){
        interactor.getMenuList()
    }

    fun getMenuItems(){
        interactor.getMenuItems(1)
    }

    fun getTableList(){
        interactor.getTables()
    }

    fun sendOrder(){
        interactor.sendOrder()
    }

    fun showTables(){
        viewState.showTables()
    }

    fun showMenu() {
        viewState.showMenu()
    }

}