package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by shohboz on 21,Январь,2021
 */
data class CashierTableData(
    var id: Int = 0,
    var color:String = "",
    var currentOrder: List<CashierOrderData> = listOf()
) {
    companion object {
        val ITEMCALLBACK = object : DiffUtil.ItemCallback<CashierTableData>() {
            override fun areItemsTheSame(oldItem: CashierTableData, newItem: CashierTableData) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CashierTableData,
                newItem: CashierTableData
            ) = oldItem.currentOrder == newItem.currentOrder
        }
    }
}