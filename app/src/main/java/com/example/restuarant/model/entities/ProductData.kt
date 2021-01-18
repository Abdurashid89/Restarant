package com.example.restuarant.model.entities

import java.util.*

data class ProductData(
    val id: Long,
    val photoId: UUID,
    val name: String,
    val description: String,
    val categoryId: Long
)