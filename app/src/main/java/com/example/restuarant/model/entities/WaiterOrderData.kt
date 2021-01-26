package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class WaiterOrderData(
    var productName:String,
    var productPrice:Int,
    var productCount:Int,
    var productTotalPrice:Int
) {

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<WaiterOrderData>(){
            override fun areItemsTheSame(
                oldItem: WaiterOrderData,
                newItem: WaiterOrderData
            ): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(
                oldItem: WaiterOrderData,
                newItem: WaiterOrderData
            ): Boolean {
                return oldItem.productName==newItem.productName
            }

        }
    }
}