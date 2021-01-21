package com.example.restuarant.presentation.were_house.add_product

import android.net.Uri
import com.example.restuarant.model.entities.BrandInData
import com.example.restuarant.model.entities.CategoryInData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface AddProductView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    fun makeLoadingVisible(status: Boolean)

    fun setImage(uri: Uri)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDialog(message: String, status: Boolean)

    fun listCategory(list: List<CategoryInData>)
    fun listBrand(list: List<BrandInData>)
    fun errorOrNull(str: String)

    fun productYON(status: Boolean, message: String)
}