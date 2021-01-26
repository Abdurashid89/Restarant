package com.example.restuarant.presentation.waiter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WaiterView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message:String)

    fun makeLoadingVisible(status:Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String,status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openClientCountDialog()


    fun showTables()


    fun showMenu()


    fun changeColor()
}