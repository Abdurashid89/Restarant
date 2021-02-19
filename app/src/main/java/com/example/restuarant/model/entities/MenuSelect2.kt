package com.example.restuarant.model.entities

/**
 * # Created by Elyor on 10,February,2021 #
 */
data class MenuSelect2(
    var id:Int,
    var count:Int,
    var menu:MenuData,
    var createdAt:String,
    var updatedAt:String,
    var cooker:Boolean
) {
}