package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

class CategoryItemData(
    var name: String,
    var price: Int,
    var description:String,
    var photo:String
) {

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<CategoryItemData>(){
            override fun areItemsTheSame(
                oldItem: CategoryItemData,
                newItem: CategoryItemData
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CategoryItemData,
                newItem: CategoryItemData
            ): Boolean {
                return oldItem.description == newItem.description
            }

        }
    }

}