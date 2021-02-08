package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class CategoryData(

    var id: Int,
    var name: String,
    var parentId: Any,
    var parentName: Any,
    var photoId:Any

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
