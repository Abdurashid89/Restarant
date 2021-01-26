package com.example.restuarant.presentation.cashier

import com.example.restuarant.model.entities.TableResData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by shohboz on 18,Январь,2021
 */

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface CashierView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)
    fun submitTables(list: List<TableResData>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)
}