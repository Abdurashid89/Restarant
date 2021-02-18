package com.example.restuarant.presentation.were_house.add_product

import com.example.restuarant.model.entities.ProductInData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Davronbek on 09,Февраль,2021
 */
@StateStrategyType(AddToEndSingleTagStrategy::class)
interface EnterProductView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDialog(message: String, status: Boolean)

    fun errorOrNull(string: String)

    fun productYON(status: Boolean, message: String)

    fun listProducts(list: List<ProductInData>)

    fun clearAllOldData()
}