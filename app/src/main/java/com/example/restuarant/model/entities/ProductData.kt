package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class ProductData(
    val name: String,
    val type: String,
    val sold: Boolean,
    val incomePrice: Double,
    val sellPrice: Double,
    val presentCount: Double,
    val minCount: Double
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductData>() {
            override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
                oldItem.type == newItem.type
        }
    }
}

data class ProductHistoryData(
    val input: Boolean,
    val incomePrice: Double,
    val sellPrice: Double,
    val presentCount: Double,
    val createdAt: String,
    val updatedAt: String,
    val sold: Boolean,
    val name: String,
    val type: String
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductHistoryData>() {
            override fun areItemsTheSame(oldItem: ProductHistoryData, newItem: ProductHistoryData): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ProductHistoryData, newItem: ProductHistoryData): Boolean =
                oldItem.type == newItem.type
        }
    }
}