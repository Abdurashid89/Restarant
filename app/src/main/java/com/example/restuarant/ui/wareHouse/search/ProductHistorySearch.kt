package com.example.restuarant.ui.wareHouse.search

import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.model.entities.ProductHistoryData
import com.example.restuarant.ui.wareHouse.history.HistoryAdapter
import java.util.*

class ProductHistorySearch(
    private val adapter: HistoryAdapter,
    private val orderList: ArrayList<ProductHistoryData>
) : ITextWatcher {
    private var contents: ArrayList<ProductHistoryData> = ArrayList()
    private fun publishData(searchedUser: ArrayList<ProductHistoryData>) {
        adapter.updateProductHistories(searchedUser)
    }

    override fun onTextChanged(text: String) {
        if (text.isEmpty()) {
            publishData(contents)
        } else {
            val searchedUser = ArrayList<ProductHistoryData>()
            for (data in contents) {
                if (data.name.contains(text) /*|| data.name.contains(text)*/) {
                    searchedUser.add(data)
                }
            }
            publishData(searchedUser)
        }
    }

    init {
        contents = orderList.clone() as ArrayList<ProductHistoryData>
    }
}