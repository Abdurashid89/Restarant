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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getTables()

        viewState.makeLoadingVisible(true)
        dialogOpen(true)
    }

    fun dialogOpen(status:Boolean){
        viewState.openDialog(status)
    }

    @SuppressLint("CheckResult")
    fun getTables(){
        viewState.makeLoadingVisible(true)
        interactor.getAllTables()
            .doOnSubscribe {
//                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
//                viewState.makeLoadingVisible(false)
            }
            .subscribe({
               viewState.submitTables(it.objectData)
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            })
    }

    fun onBackPressed(){
        router.exit()
    }

}