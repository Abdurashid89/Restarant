package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class GetResponseData<T>(
    var objectDate: T,
    var totalElements: Int = 0,
    var currentPage: Int = 0
)

data class ProductInData(
    var productId: Int = 0,
    var name: String,
    var incomePrice: Double,
    var sellPrice: Double,
    var count: Int,
    var minCount: Int,
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductInData>() {
            override fun areItemsTheSame(oldItem: ProductInData, newItem: ProductInData): Boolean =
                oldItem.productId == newItem.productId

            override fun areContentsTheSame(
                oldItem: ProductInData,
                newItem: ProductInData
            ): Boolean =
                oldItem.name == newItem.name

        }
    }
}