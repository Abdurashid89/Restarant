package com.example.restuarant.model.entities.check

data class PaidCheck(
    val id: Int,
    val paidPrice: String,
    val cashBack: Long,
    val payType: String,
    val cheque: String
)