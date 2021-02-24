package com.example.restuarant.presentation.were_house.product_history

import com.example.restuarant.model.interactor.AddProductInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by Davronbek on 17,Февраль,2021
 */

@InjectViewState
class HistoryPresenter @Inject constructor(
    private val router: FlowRouter,
    private val addProductInteractor: AddProductInteractor,
    private val prefs: Prefs
) : BasePresenter<HistoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        inputProductHistory()
    }

    fun onBackPressed() {
        router.exit()
    }

    private fun inputProductHistory() {
        addProductInteractor.inputProductHistory()
            .doOnSubscribe {

            }.doAfterTerminate {

            }.subscribe({
                viewState.listProducts(it)
//                viewState.showMessage("")
            }, {
//                viewState.showMessage(it.toString())
            }).connect()
    }

    private fun outputProductHistory() {
        addProductInteractor.outputProductHistory()
            .doOnSubscribe {

            }.doAfterTerminate {

            }.subscribe({
                viewState.listProducts(it)
//                viewState.showMessage("")
            }, {
//                viewState.showMessage(it.toString())
            }).connect()
    }

}