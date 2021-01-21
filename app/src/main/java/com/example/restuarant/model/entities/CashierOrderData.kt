package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by shohboz on 21,Январь,2021
 */
data class CashierOrderData(
    var id:Int,
    var mealName:String = "",
    var count:Double = 0.0,
    var price:Double = 0.0,
    var total:String = ""
) {
    companion object {
        val ITEMCALLBACK = object : DiffUtil.ItemCallback<CashierOrderData>() {
            override fun areItemsTheSame(
                oldItem: CashierOrderData,
                newItem: CashierOrderData
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CashierOrderData,
                newItem: CashierOrderData
            ) = oldItem.mealName == newItem.mealName &&  oldItem.count == newItem.count &&
                    oldItem.price == newItem.price &&
                    oldItem.total == newItem.total
        }
    }
}