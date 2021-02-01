package com.example.restuarant.model.entities

data class OrderSendData(
    var address:String,
    var paid:String,
    var status:String,
    var orderList: List<WaiterOrderData>,
    var tableId:Int,
    var totalPrice:Int
) {


}