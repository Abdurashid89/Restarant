package com.example.restuarant.model.entities

data class ProductData(
    val name: String,
    val type: String,
    val sold: Boolean,
    val incomePrice: Double,
    val sellPrice: Double,
    val presentCount: Double,
    val minCount: Double
)