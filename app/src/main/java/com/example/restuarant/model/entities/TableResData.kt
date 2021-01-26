package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class TableResData(
    var active: Boolean = false,
    var createdAt: String = "",
    var id: Int = 0,
    var name: String = "",
    var updatedAt: String = ""
) {
    companion object {
        val ITEMCALLBACK = object : DiffUtil.ItemCallback<TableResData>() {
            override fun areItemsTheSame(
                oldItem: TableResData,
                newItem: TableResData
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TableResData,
                newItem: TableResData
            ) = oldItem.active == newItem.active && oldItem.name == newItem.name
        }
    }
}