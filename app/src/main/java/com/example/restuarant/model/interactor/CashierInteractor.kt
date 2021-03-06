package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.check.CheckData
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.model.entities.ResData
import com.example.restuarant.model.entities.TableData
import com.example.restuarant.model.entities.check.PaidCheck
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by shohboz on 18,Январь,2021
 */

class CashierInteractor @Inject constructor(
    private val api: ResApi
) {
    fun getAllTables(): Single<ResData<List<TableData>>> {
        return api.getAllTables().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun loadOrderById(id: Int): Single<ResData<OrderGetData>> {
        return api.getTableInfo(id).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendToServer(paidCheck: PaidCheck): Single<CheckData> {
        return api.sendCheckByOrderId(paidCheck).map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadHistory(): Single<ResData<List<OrderGetData>>> {
        return api.getAllHistory()
            .map {
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}