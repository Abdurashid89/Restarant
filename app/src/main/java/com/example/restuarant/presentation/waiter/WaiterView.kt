package com.example.restuarant.presentation.waiter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WaiterView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message:String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun makeLoadingVisible(status:Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String,status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openClientCountDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showTables()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMenu()


}