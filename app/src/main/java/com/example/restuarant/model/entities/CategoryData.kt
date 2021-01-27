package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class CategoryData(
    var menuId: Int,
    var photoId: String,
    var name: String,
    var descprition: String,
    var menuItems:List<CategoryItemData>
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CategoryData>() {
            override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
                return oldItem == newItem
            }

        }
    }
}
