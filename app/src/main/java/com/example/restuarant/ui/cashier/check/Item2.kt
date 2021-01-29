package com.example.restuarant.ui.cashier.check

import com.example.restuarant.model.entities.CashierOrderData

/**
 * Created by shohboz on 28,Январь,2021
 */

class Item2(val list: List<CashierOrderData>) {
    fun getItemNameList():String{
        var itemList = ""
        list.forEach {
            val word = "<p class=\\\"products\\\">${it.mealName}  ${it.count}</p"
            itemList += word

        }
        return itemList
    }

    fun getPriceList():String{
        var price = ""
        list.forEach {
            price += "<p class=\\\"products\\\">${it.price} sum</p>"

        }
        return price
    }

}