package com.example.restuarant.model.entities

/**
 * # Created by Elyor on 10,February,2021 #
 */
data class OrderGetData(
    var orderType:String,
    var orderStatus:String,
    var payStatus:String,
    var feedBackStatus:Int,
    var menuSelectionList: List<MenuSelect2>,
    var orderDateTime:String,
    var orderPrice:Double,
) {
}