package com.example.restuarant.presentation.waiter

import android.annotation.SuppressLint
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.OrderSendData
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
        getMenu()
        getTableList()
    }

    fun openClientDialog(){
        viewState.openClientCountDialog()
    }

    fun onBackPressed(){
        router.exit()
    }

    fun getMenu(){
        interactor.getMenuList()
            .doOnSubscribe {
                viewState.showProgress(DI.MENU_ITEMS_PROGRESS,true)
            }
            .doAfterTerminate {
                viewState.showProgress(DI.MENU_ITEMS_PROGRESS,false)
            }
            .subscribe({
                viewState.getMenu(it)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            }).connect()
    }


    fun getMenuItems(categoryId:Int){
        interactor.getMenuItems(categoryId)
            .doOnSubscribe {
                viewState.showProgress(DI.MENU_ITEMS_PROGRESS,false)
            }.doAfterTerminate {
                viewState.showProgress(DI.MENU_ITEMS_PROGRESS,false)
            }.subscribe({
                viewState.getItemsById(it)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            }).connect()
    }

    fun getTableList(){
        interactor.getTables()
            .doOnSubscribe {
                viewState.showProgress(DI.TABLES_PROGRESS,true)
            }
            .doAfterTerminate {
                viewState.showProgress(DI.TABLES_PROGRESS,false)
            }
            .subscribe({
                 viewState.getTables(it.objectData)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            }).connect()
    }

    @SuppressLint("CheckResult")
    fun sendOrder(data:OrderSendData){
        interactor.sendOrder(data)
            .doOnSubscribe {
                viewState.showProgress(DI.ORDER_PROGRESS,true)
            }
            .doAfterTerminate {
                viewState.showProgress(DI.ORDER_PROGRESS,false)
            }
            .subscribe({
                viewState.clearList(true)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            })
    }

    @SuppressLint("CheckResult")
    fun orderGetData(tableId:Int) {
        interactor.getOrder(tableId)
            .doOnSubscribe {

            }
            .doAfterTerminate {

            }
            .subscribe({
                       viewState.getOrderInfo(it.objectData)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            })
    }

    fun showTables(){
        viewState.showTables()
    }

    fun showMenu() {
        viewState.showMenu()
    }

    fun changeColor(){
        viewState.changeColor()
    }

    fun totalSum(){
        viewState.totalSum()
    }

    fun showProgress(){

    }

}