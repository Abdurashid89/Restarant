package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.GetResponseData
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class ProductIntegrator @Inject constructor(
    private val api: ResApi
) {
    private val executor = Executors.newSingleThreadExecutor()
    fun getAllProduct(page: Int, size: Int): Single<GetResponseData<List<ProductInData>>> {
        return api.getAllProduct(page, size, "all").map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}