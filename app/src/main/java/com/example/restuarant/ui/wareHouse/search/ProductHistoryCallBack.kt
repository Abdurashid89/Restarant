package com.example.restuarant.ui.wareHouse.search

import androidx.recyclerview.widget.DiffUtil
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.model.entities.ProductHistoryData
import com.example.restuarant.model.entities.ProductInData
import java.util.*

class ProductHistoryCallBack(
    private val newList: ArrayList<ProductHistoryData>,
    private val oldList: ArrayList<ProductHistoryData>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val  product = oldList[oldItemPosition]
        val  product2 = newList[newItemPosition]
        return product.name  == product2.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOrder = oldList[oldItemPosition]
        val newOrder = newList[newItemPosition]
        return oldOrder == newOrder
    }
}