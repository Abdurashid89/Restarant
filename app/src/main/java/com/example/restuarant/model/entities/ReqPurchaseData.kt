package com.example.restuarant.model.entities

/**
 * Created by Davronbek on 15,Февраль,2021
 */
data class ReqPurchaseData (
    val reqPurchaseElementsList: List<ProductInData>,
    var restaurantId: Int = 5
)