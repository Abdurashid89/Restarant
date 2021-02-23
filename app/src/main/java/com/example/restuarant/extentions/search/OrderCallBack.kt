package com.example.restuarant.extentions.search

import androidx.recyclerview.widget.DiffUtil
import com.example.restuarant.model.entities.OrderGetData
import java.util.*

class OrderCallBack(
    private val newList: ArrayList<OrderGetData>,
    private val oldList: ArrayList<OrderGetData>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, table) = oldList[oldItemPosition]
        val (_, table1) = newList[newItemPosition]
        return table.id  == table1.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOrder = oldList[oldItemPosition]
        val newOrder = newList[newItemPosition]
        return oldOrder == newOrder
    }
}