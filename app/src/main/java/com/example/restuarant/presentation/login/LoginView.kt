package com.example.restuarant.presentation.login

import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.model.entities.UnPaidData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by shohboz on 11,Январь,2021
 */
@StateStrategyType(AddToEndSingleTagStrategy::class)
interface LoginView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)

    fun ordersFromServer(list: List<OrderGetData>)

}