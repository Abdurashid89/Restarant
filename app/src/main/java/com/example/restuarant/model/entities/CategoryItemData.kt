package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

class CategoryItemData(
    var id:Int,
    var photoId:String,
    var name: String,
    var description:String,
    var price: Double,
    var active:Boolean,
    var evaluation:Int,
    var categoryId:Int

) {

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<CategoryItemData>(){
            override fun areItemsTheSame(
                oldItem: CategoryItemData,
                newItem: CategoryItemData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryItemData,
                newItem: CategoryItemData
            ): Boolean {
                return oldItem.description == newItem.description && oldItem.name == newItem.name
                        && oldItem.evaluation == newItem.evaluation && oldItem.photoId == newItem.photoId
                        && oldItem.active == newItem.active
            }

        }
    }

}