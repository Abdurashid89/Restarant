package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

class TableData (
    val number:String,
    val active:Boolean,
    val people:Int
        ){

    companion object{
        val ITEM_CALLBACK = object :DiffUtil.ItemCallback<TableData>(){
            override fun areItemsTheSame(oldItem: TableData, newItem: TableData): Boolean {
                return oldItem.number==newItem.number
            }

            override fun areContentsTheSame(oldItem: TableData, newItem: TableData): Boolean {
                return oldItem.people==newItem.people && oldItem.number==newItem.number
            }

        }
    }

}