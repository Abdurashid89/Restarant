package com.example.restuarant.model.entities

/**
 * # Created by Elyor on 10,February,2021 #
 */
data class OrderGetData(
    var orderType:String,
    var orderStatus:String,
    var feedBackStatus:Int,
    var payStatus:Any,
    var orderDateTime:String,
    var menuSelection: List<MenuSelect2>,
    var orderPrice:Double,
    var id:Long
) {

}