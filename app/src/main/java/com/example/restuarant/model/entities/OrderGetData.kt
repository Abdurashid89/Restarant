package com.example.restuarant.model.entities

/**
 * # Created by Elyor on 10,February,2021 #
 */
data class OrderGetData(
    var id:Long,
    var table: Table,
    var feedBackStatus:Int,
    var orderStatus:String,
    var orderType:String,
    var cheque:String,
    var createdAt:String,
    var paidPrice:Double,
    var orderPrice:Double,
    var orderDateTime:String,
    var payType:String,
    var cashBack:Double,
    var payStatus:String,
    var menuSelection: List<MenuSelect2>,
    var updateAt:String
)

data class Table(
    var name:Int,
    var id:Long
)