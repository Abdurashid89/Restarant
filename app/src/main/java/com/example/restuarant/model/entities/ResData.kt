package com.example.restuarant.model.entities

data class ResData<T>(
    var success: Boolean = false,
    var message: String = "",
    var objectData: T
)