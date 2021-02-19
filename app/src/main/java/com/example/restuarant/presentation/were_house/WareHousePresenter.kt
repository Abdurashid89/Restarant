package com.example.restuarant.presentation.were_house

import android.annotation.SuppressLint
import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.interactor.WareHouseInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

@InjectViewState
class WareHousePresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: WareHouseInteractor,
    private val prefs: Prefs,
) : BasePresenter<WareHouseView>() {

    fun onBackPressed() {
        router.exit()
    }

    fun openHistoryScreen() {
        router.newChain(Screens.History)
    }


    @SuppressLint("CheckResult")
    fun addProduct(data: ProductData) {
        interactor.product(data)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }.doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }
            .subscribe({
                viewState.openErrorDialog("Product successfully added", true)
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
            }).connect()
    }

}