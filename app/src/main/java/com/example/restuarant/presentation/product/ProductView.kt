package com.example.restuarant.presentation.product

import com.example.restuarant.model.entities.ProductInData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface ProductView : MvpView {

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)
    fun visibleProgressBar(status: Boolean)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun openErrorDialog(message: String, status: Boolean)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun openProductItemDialog(item: ProductInData)


    fun listProduct(list: List<ProductInData>)

    @StateStrategyType(AddToEndSingleTagStrategy::class)
    fun productItem(item: ProductInData)

    fun responseError()
}