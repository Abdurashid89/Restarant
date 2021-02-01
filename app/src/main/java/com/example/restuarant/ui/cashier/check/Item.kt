package com.example.restuarant.ui.cashier.check

import android.util.Log
import com.example.restuarant.model.entities.CashierOrderData


/**
 * Created by DostonbekIbragimov on 28.01.2021 Email: idostonbek1230@mail.ru
 */
class Item(val list: List<CashierOrderData>) {
    fun getItemNameList(): String {
        var itemList = ""
        list.forEach {
            val word = "<p class=\\\"products\\\">${it.mealName}  ${it.count}</p>"
            itemList += word
        }
        Log.d("TTT", "list : $itemList")
//        itemList.replace("\"")
        return itemList
    }

    fun getPriceList(): String {
        var itemList = ""
        list.forEach {
            itemList += "<p class=\\\"products\\\">${it.price} sum</p>"
        }
        return itemList
    }
}