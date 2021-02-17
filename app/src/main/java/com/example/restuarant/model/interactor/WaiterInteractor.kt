package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.*
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WaiterInteractor @Inject constructor(
    val api: ResApi
) {
    fun getMenuList(): Single<ResData<List<CategoryData>>> {
        return api.getAllMenus().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMenuItems(categoryId: Int): Single<List<CategoryItemData>> {
        return api.getItemsById(categoryId).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTables(): Single<ResData<List<TableData>>> {
        return api.getAllTables().map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendOrder(data: OrderSendData): Single<ResOrderData> {
        return api.sendOrder(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getOrder(tableId:Int):Single<ResData<OrderGetData>>{
        return api.getTableInfo(tableId).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun orderUpdate(data: OrderUpdateData):Single<ResOrderData>{
        return api.orderUpdate(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}