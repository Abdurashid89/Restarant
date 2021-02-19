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
    private val interactor: AddProductInteractor,
    private val prefs: Prefs
) : BasePresenter<HistoryView>() {

    fun onBackPressed() {
        router.exit()
    }

}