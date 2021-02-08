package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class WaiterOrderData(
    var id:Int,
    var productName:String,
    var productPrice:Double,
    var productCount:Int,
    var productTotalPrice:Double
) {

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<WaiterOrderData>(){
            override fun areItemsTheSame(
                oldItem: WaiterOrderData,
                newItem: WaiterOrderData
            ): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WaiterOrderData,
                newItem: WaiterOrderData
            ): Boolean {
                return oldItem.productCount==newItem.productCount && oldItem.productName==newItem.productName
                        && oldItem.productPrice==newItem.productPrice && oldItem.productTotalPrice==newItem.productTotalPrice
            }

        }
    }
}