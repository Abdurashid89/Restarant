package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by shohboz on 02,Февраль,2021
 */

data class CashierHistoryData(
    var id:Int,
    var orderPrice:String = "",
    var orderType:String = "",
    var cashBack:String = "",
    var orderDetail:String = ""
) {
    companion object {
        val ITEMCALLBACK = object : DiffUtil.ItemCallback<CashierHistoryData>() {
            override fun areItemsTheSame(
                oldItem: CashierHistoryData,
                newItem: CashierHistoryData
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CashierHistoryData,
                newItem: CashierHistoryData
            ) = oldItem.orderPrice == newItem.orderPrice &&  oldItem.orderType == newItem.orderType &&
                    oldItem.cashBack == newItem.cashBack &&
                    oldItem.orderDetail == newItem.orderDetail
        }
    }
}