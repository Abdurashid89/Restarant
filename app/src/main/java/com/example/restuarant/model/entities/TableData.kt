package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class TableData (
    val id:Int,
    val createdAt:String,
    val updatedAt:String,
    val active:Boolean,
    val count:Int
        ){

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<TableData>(){
            override fun areItemsTheSame(oldItem: TableData, newItem: TableData): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: TableData, newItem: TableData): Boolean {
                return oldItem.count==newItem.count && oldItem.id==newItem.id
            }

        }
    }

}