package com.example.restuarant.presentation.were_house.product_history

import com.example.restuarant.model.entities.ProductHistoryData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Davronbek on 17,Февраль,2021
 */

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface HistoryView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDialog(message: String, status: Boolean)

    fun errorOrNull(str: String)

    fun productYON(status: Boolean, message: String)
    fun listProducts(list: List<ProductHistoryData>)
}