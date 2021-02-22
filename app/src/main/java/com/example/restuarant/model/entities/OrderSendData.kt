package com.example.restuarant.model.entities

data class OrderSendData(
    var address:String,
    var orderStatus:String,
    var orderPrice:Double,
    var orderType:String,
    var PAID:String,
    var tableId:Int,
    var roomsId: Int?,
    var menuSelectionList: List<MenuSelect>,
    var restaurantId:Int
)