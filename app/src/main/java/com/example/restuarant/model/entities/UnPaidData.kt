package com.example.restuarant.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Shohboz Qoraboyev on 17,Февраль,2021
 */
@Entity()
data class UnPaidData(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val orderStatus: String,
    val orderDateTime: String,
    val orderPrice: Double,
    val feedBackStatus: Int,
    val orderType: String,
    val payStatus: String,
    val createdAt: String,
    val updatedAt: String
)