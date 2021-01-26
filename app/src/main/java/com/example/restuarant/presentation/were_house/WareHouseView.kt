package com.example.restuarant.presentation.were_house

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Davronbek on 22,Январь,2021
 */

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface WareHouseView : MvpView {

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)
}