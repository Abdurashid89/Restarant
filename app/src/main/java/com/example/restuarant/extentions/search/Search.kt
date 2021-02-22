package com.example.restuarant.extentions.search

import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.ui.cashier.CashierHistoryAdapter
import java.util.*

class Search(
    private val adapter: CashierHistoryAdapter,
    private val orderList: ArrayList<OrderGetData>
) : ITextWatcher {
    private var contents: ArrayList<OrderGetData> = ArrayList()
    private fun publishData(searchedUser: ArrayList<OrderGetData>) {
        adapter.updateUsers(searchedUser)
    }

    override fun onTextChanged(text: String) {
        if (text.isEmpty()) {
            publishData(contents)
        } else {
            val searchedUser = ArrayList<OrderGetData>()
            for (data in contents) {
                if (data.table.id.toString().contains(text)) {
                    searchedUser.add(data)
                }
            }
            publishData(searchedUser)
        }
    }

    init {
        contents = orderList.clone() as ArrayList<OrderGetData>
    }
}