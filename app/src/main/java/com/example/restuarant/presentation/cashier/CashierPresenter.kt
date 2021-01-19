package com.example.restuarant.presentation.cashier

import android.annotation.SuppressLint
import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.model.interactor.CashierInteractor
import com.example.restuarant.model.interactor.CookerInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import com.example.restuarant.presentation.signup.SignUpView
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by shohboz on 18,Январь,2021
 */

@InjectViewState
class CashierPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: CashierInteractor,
    private val prefs: Prefs
): BasePresenter<CashierView>(){

    fun onBackPressed(){
        router.exit()
    }

    @SuppressLint("CheckResult")
    fun register(data: RegisterData){
        viewState.makeLoadingVisible(true)
        interactor.register(data)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }
            .subscribe({
                prefs.accessToken = it.body.accessToken
                router.newChain(Screens.Login)
                viewState.showMessage("Success")
            },{
                viewState.openErrorDialog(errorResponse(it),false)
            })
    }
}