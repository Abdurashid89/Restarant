package com.example.restuarant.presentation.were_house.add_product

import android.annotation.SuppressLint
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.model.entities.ReqPurchaseData
import com.example.restuarant.model.interactor.AddProductInteractor
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by Davronbek on 10,Февраль,2021
 */
@InjectViewState
class EnterProductPresenter @Inject constructor(
    private val interactor: AddProductInteractor
) : BasePresenter<EnterProductView>() {
    var page = 0
    var size = 20
    var totalElements = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getProduct()
    }

//    @SuppressLint("CheckResult")
//    fun purchaseProduct(data: ReqPurchaseData) {
//        interactor.productPurchase(data)
//            .doOnSubscribe {
//                viewState.makeLoadingVisible(true)
//            }
//            .doAfterTerminate {
//                viewState.makeLoadingVisible(false)
//            }.subscribe({
//                viewState.showMessage("Product Successfully added")
//                viewState.clearAllOldData()
//            }, {
//                viewState.showMessage(it.errorResponse())
//            }).connect()
//    }

    fun getProduct() {
        interactor.getAllProduct()
            .doOnSubscribe {

            }.doAfterTerminate {

            }.subscribe({
                viewState.listProducts(it.objectDate)
                viewState.showMessage(it.objectDate.size.toString())
            }, {
                viewState.showMessage(it.errorResponse())
            }).connect()
    }

}