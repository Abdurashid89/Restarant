package com.example.restuarant.ui.cashier.check

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Shohboz Qoraboyev on 19,Февраль,2021
 */

data class CookerCheckData(
    val name: String,
    val latest: String
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CookerCheckData>() {
            override fun areItemsTheSame(
                oldItem: CookerCheckData,
                newItem: CookerCheckData
            ) = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: CookerCheckData,
                newItem: CookerCheckData
            ) = oldItem.latest == newItem.latest
        }
    }
}

