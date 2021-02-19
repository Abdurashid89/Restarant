package com.example.restuarant.presentation.cashier

import android.annotation.SuppressLint
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.check.PaidCheck
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

    fun loadOrderByTableId(id: Int) {
        interactor.loadOrderById(id)
            .doOnSubscribe {
                viewState.showProgress(true)
            }
            .doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({
                viewState.addTableOrder(it.objectData)
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


    fun sendPay(paidCheck: PaidCheck) {
        interactor.sendToServer(paidCheck)
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }.subscribe({
                viewState.showMessage(it.message)
            }, {
                viewState.showMessage(it.errorResponse())
            }).connect()
    }

    fun loadHistory() {
        interactor.loadHistory().doOnSubscribe {
            viewState.showProgress(true)
        }
            .doAfterTerminate {
                viewState.showProgress(false)
            }.subscribe({
                viewState.allHistory(it.objectData)
            }, {
                viewState.showMessage(it.errorResponse())
            }).connect()
    }


}