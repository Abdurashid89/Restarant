package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil
import java.sql.Timestamp

data class GetResponseData<T>(
    var objectDate: T,
    var totalElements: Int = 0,
    var currentPage: Int = 0
)

data class ProductInData(
    var id: Int,
    var type: String,
    var sold: Boolean,
    var name: String,
    var incomePrice: Double,
    var sellPrice: Double,
    var presentCount: Double,
    var minCount: Double
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductInData>() {
            override fun areItemsTheSame(oldItem: ProductInData, newItem: ProductInData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductInData,
                newItem: ProductInData
            ): Boolean =
                oldItem.name == newItem.name

        }
    }
}