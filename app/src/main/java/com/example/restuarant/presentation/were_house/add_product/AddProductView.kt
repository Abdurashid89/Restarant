package com.example.restuarant.presentation.were_house.add_product

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface AddProductView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDialog(message: String, status: Boolean)

    fun errorOrNull(str: String)

    fun productYON(status: Boolean, message: String)
}