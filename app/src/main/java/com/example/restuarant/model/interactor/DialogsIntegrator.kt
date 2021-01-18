package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.BrandInData
import com.example.restuarant.model.entities.CategoryInData
import com.example.restuarant.model.entities.GetResponseData
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DialogsIntegrator @Inject constructor(
    private val api: ResApi
) {
    fun getCategory(
        page: Int,
        size: Int,
        search: String
    ): Single<GetResponseData<List<CategoryInData>>> {
        return api.getAllCategory(page, size, search).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCategoryWithSearch(page: String): Single<List<CategoryInData>> {
        return api.getCategorySearch(page).map {
            it
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBrandWithSearch(page: String): Single<List<BrandInData>> {
        return api.getBrandSearch(page).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}


