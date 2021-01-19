package com.example.restuarant.presentation.cooker

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by shohboz on 18,Январь,2021
 */

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface CookerView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)
}