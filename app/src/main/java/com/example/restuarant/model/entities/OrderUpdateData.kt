package com.example.restuarant.model.entities

/**
 * # Created by Elyor on 15,February,2021 #
 */
data class OrderUpdateData(
    var id:Long,
    var address:String,
    var orderStatus:String,
    var orderPrice:Double,
    var orderType:String,
    var PAID:String,
    var tableId:Int,
    var roomsId: Int?,
    var menuSelectionList: List<MenuSelect>,
    var restaurantId:Int
) {
}