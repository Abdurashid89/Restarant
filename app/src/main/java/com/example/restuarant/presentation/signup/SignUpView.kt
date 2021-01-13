package com.example.restuarant.presentation.signup

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignUpView:MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message:String)

    fun makeLoadingVisible(status:Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String,status: Boolean)

}