package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.MessageData
import com.example.restuarant.model.entities.MessageDataWithoutMessageType
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddProductInteractor @Inject constructor(
    private val api: ResApi
) {
    fun product(data: ProductData): Single<MessageData> {
        return api.addProduct(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
    }

    fun productExistOrNot(name: String): Single<MessageDataWithoutMessageType> {
        return api.productExistOrNot(name).map {
            it
        }.subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {  }
    }
}