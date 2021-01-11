package com.example.restuarant.presentation.login

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

    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)
}