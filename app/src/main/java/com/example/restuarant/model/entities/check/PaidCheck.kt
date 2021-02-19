package com.example.restuarant.model.entities.check

data class PaidCheck(
    val id: Long,
    val paidPrice: Double,
    val cashBack: Double,
    val payType: String,
    val cheque: String
)