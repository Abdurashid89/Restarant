package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.*
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
            .doOnError { }
    }

    fun inputProduct(data: ProductInData): Single<MessageData> {
        return api.inputProduct(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
    }

    fun outputProduct(data: ProductInData): Single<MessageData> {
        return api.outputProduct(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
    }

    fun getAllProduct(): Single<GetResponseData<List<ProductInData>>> {
        return api.getAllProduct().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun inputProductHistory(): Single<List<ProductHistoryData>> {
        return api.productInputHistory().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun outputProductHistory(): Single<List<ProductHistoryData>> {
        return api.productOutputHistory().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}