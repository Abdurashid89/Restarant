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

//    "address":"Toshkent",
//"orderStatus":"ON_THE_WAY",
//"orderPrice":550.0,
//"orderType":"DELIVERY",
//"PAID":"PAID",
//"tableId":null,
//"roomsId":null,
//"menuSelectionList":[{
//    "count":5,
//    "menu":9
//}],
//"restaurantId":1
) {


}