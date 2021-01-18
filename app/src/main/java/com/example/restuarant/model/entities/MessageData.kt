package com.example.restuarant.model.entities

data class MessageData(
    var message: String = "",
    var success: Boolean = false,
    var messageType: String = ""
)

data class MessageDataWithoutMessageType(
    var message: String = "",
    var success: Boolean = false
)