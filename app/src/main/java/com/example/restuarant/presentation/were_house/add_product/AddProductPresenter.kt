package com.example.restuarant.presentation.were_house.add_product

import android.annotation.SuppressLint
import android.net.Uri
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.interactor.AddProductInteractor
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@InjectViewState
class AddProductPresenter @Inject constructor(
    private val router: FlowRouter,
    private val intercepter: AddProductInteractor
) : BasePresenter<AddProductView>() {
    var page = 0
    var size = 20
    var totalElements = 0

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
                viewState.openDialog(it.errorResponse(), false)
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


    fun onBackPressed() {
        router.exit()
    }

    fun sendImageUri(path: String, uri: Uri) {
        viewState.makeLoadingVisible(true)
        val file = File(path)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", file.name, requestFile)

        intercepter

    }

}