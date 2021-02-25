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