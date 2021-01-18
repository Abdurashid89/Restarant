package com.example.restuarant.presentation.were_house.add_product

import android.annotation.SuppressLint
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.CategoryInData
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.interactor.AddProductInteractor
import com.example.restuarant.model.interactor.DialogsIntegrator
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class AddProductPresenter @Inject constructor(
    private val router: FlowRouter,
    private val intercepter: AddProductInteractor,
    private val dialogsIntegrator: DialogsIntegrator
) : BasePresenter<AddProductView>() {
    var page = 0
    var size = 20
    var totalELements = 0
    private val activeList = ArrayList<CategoryInData>()

    @SuppressLint("CheckResult")
    fun addProduct(data: ProductData) {
        intercepter.product(data)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }.subscribe({
                viewState.openDialog("Product Successfully added", true)
            }, {
                viewState.openDialog(errorResponse(it), false)
            }).connect()
    }

    fun productExistOrNot(name: String) {
        intercepter.productExistOrNot(name)
            .doOnSubscribe {}
            .doAfterTerminate {}
            .subscribe({
                viewState.productYON(it.success, it.message)
            }, {
                viewState.productYON(false, "Wrong")
            }).connect()
    }

    fun getCategorySearch(page: String) {
        dialogsIntegrator.getCategoryWithSearch(page)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }
            .subscribe({
                viewState.listCategory(it)
            }, {
                viewState.errorOrNull(errorResponse(it))
            }).connect()

    }

    fun getBrandSearch(page: String) {
        dialogsIntegrator.getBrandWithSearch(page)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }.doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }.subscribe({
                viewState.listBrand(it)
            }, {
                viewState.errorOrNull(errorResponse(it))
            }).connect()
    }

    fun onBackPressed() {
        router.exit()
    }


}