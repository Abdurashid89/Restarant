package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class CategoryData(
    var name: String,
    var photoId:String,
    var price:Int,
    var descprition: String
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CategoryData>() {
            override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
                return oldItem.descprition == newItem.descprition
            }

            override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
                return oldItem == newItem
            }

        }
    }
}
