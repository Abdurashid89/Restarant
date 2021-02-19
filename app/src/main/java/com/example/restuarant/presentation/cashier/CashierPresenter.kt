package com.example.restuarant.presentation.cashier

import android.annotation.SuppressLint
import com.example.restuarant.di.DI
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.OrderSendData
import com.example.restuarant.model.entities.OrderUpdateData
import com.example.restuarant.model.interactor.CashierInteractor
import com.example.restuarant.model.interactor.WaiterInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by shohboz on 18,Январь,2021
 */

@InjectViewState
class CashierPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: CashierInteractor,
    private val interactorWaiter: WaiterInteractor,
    private val prefs: Prefs
) : BasePresenter<CashierView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getTables()
        getMenu()
//        viewState.makeLoadingVisible(true)
//        dialogOpen(true)
    }

    fun dialogOpen(status: Boolean) {
        viewState.openDialog(status)
    }


    @SuppressLint("CheckResult")
    fun getTables() {
//        viewState.makeLoadingVisible(true)
        interactor.getAllTables()
            .doOnSubscribe {
//                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
//                viewState.makeLoadingVisible(false)
            }
            .subscribe({
                viewState.submitTables(it.objectData)
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
            })
    }

    fun onBackPressed() {
        router.exit()
    }

    fun loadOrderByTableId(id: Int,type:Int) {
        interactor.loadOrderById(id)
            .doOnSubscribe {
                viewState.showProgress(true, type)
            }
            .doAfterTerminate {
                viewState.showProgress(false, type)
            }
            .subscribe({
                viewState.addTableOrder(it.objectData,type)
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
            }).connect()
    }

    fun getMenu() {
        interactorWaiter.getMenuList()
            .doOnSubscribe {

            }
            .doAfterTerminate {

            }
            .subscribe({
                viewState.getMenu(it)
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
            }).connect()
    }


    fun getMenuItems(categoryId: Int) {
        interactorWaiter.getMenuItems(categoryId)
            .doOnSubscribe {

            }.doAfterTerminate {

            }.subscribe({
                viewState.getItemsById(it)
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
            }).connect()
    }

    fun totalSum() {
        viewState.totalSum()
    }


    fun sendPay(orderId: Long, cheque: String) {
        interactor.sendToServer(orderId, cheque)
            .doOnSubscribe {
                viewState.showProgress(true, 1)
            }.doAfterTerminate {
                viewState.showProgress(false, 1)
            }.subscribe({
                viewState.showMessage(it.message)
            }, {
                viewState.showMessage(it.message!!)
            }).connect()
    }

    fun loadHistory() {
        interactor.loadHistory().doOnSubscribe { viewState.showProgress(true, 1) }
            .doAfterTerminate {
                viewState.showProgress(false, 1)
            }.subscribe({
                viewState.allHistory(it)
            }, {

            }).connect()
    }

    @SuppressLint("CheckResult")
    fun sendOrder(data: OrderSendData){
        interactorWaiter.sendOrder(data)
            .doOnSubscribe {
                viewState.showProgress(true,2)
            }
            .doAfterTerminate {
                viewState.showProgress(false,2)
            }
            .subscribe({
                viewState.clearList(true)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            })
    }

    @SuppressLint("CheckResult")
    fun orderUpdate(data: OrderUpdateData){
        interactorWaiter.orderUpdate(data)
            .doOnSubscribe {
                viewState.showProgress(true,2)
            }
            .doAfterTerminate {
                viewState.showProgress(false,2)
            }
            .subscribe({
                viewState.clearList(true)
                viewState.showMessage("Order Updated")
            },{
//                viewState.openErrorDialog(it.errorResponse(),false)
                viewState.showMessage(it.errorResponse())
            })
    }


}