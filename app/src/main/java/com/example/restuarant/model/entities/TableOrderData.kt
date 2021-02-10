package com.example.restuarant.model.entities

data class TableOrderData(
    val feedBackStatus: Int,
    val payStatus: String? = null,
    val orderStatus: String,
    val orderDateTime: String,
    val orderType: String,
    val orderPrice: String
)

/*{
    "success": true,
    "message": "ok",
    "objectData": {
        "feedBackStatus": 0,
        "payStatus": null,
        "orderStatus": "ON_THE_WAY",
        "orderDateTime": null,
        "orderType": "DELIVERY",
        "orderPrice": 2500.0,
        "menuSelection": [
            {
                "id": 13,
                "count": 5,
                "menu": {
                    "name": "bahor",
                    "id": 9,
                    "price": 500.0
                }
            }
        ]
    }
}*/
