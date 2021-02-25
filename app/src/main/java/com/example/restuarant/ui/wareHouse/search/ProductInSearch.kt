package com.example.restuarant.ui.wareHouse.search

import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.ui.wareHouse.WareHouseAdapter
import java.util.*

class ProductInSearch(
    private val adapter: WareHouseAdapter,
    private val orderList: ArrayList<ProductInData>
) : ITextWatcher {
    private var contents: ArrayList<ProductInData> = ArrayList()
    private fun publishData(searchedUser: ArrayList<ProductInData>) {
        adapter.updateProducts(searchedUser)
    }

    override fun onTextChanged(text: String) {
        if (text.isEmpty()) {
            publishData(contents)
        } else {
            val searchedUser = ArrayList<ProductInData>()
            for (data in contents) {
                if (data.name.contains(text) /*|| data.name.contains(text)*/) {
                    searchedUser.add(data)
                }
            }
            publishData(searchedUser)
        }
    }

    init {
        contents = orderList.clone() as ArrayList<ProductInData>
    }
}