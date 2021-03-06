package com.example.restuarant.presentation.cashier

import com.example.restuarant.model.entities.*
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

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun makeLoadingVisible(status: Boolean)
    fun submitTables(list: List<TableData>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDialog(status: Boolean)


    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)

    fun addTableOrder(objectData: OrderGetData,type: Int)
    fun showProgress(isShow: Boolean,type:Int)

    fun getItemsById(list: List<CategoryItemData>)
    fun getMenu(list: ResData<List<CategoryData>>)
    fun clearList(type:Boolean)
    fun totalSum()
    fun allHistory(orderGetData: List<OrderGetData>)
    fun showTables()
}