package com.example.restuarant.ui.cashier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemHistoryChashierBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.CashierHistoryData

/**
 * Created by shohboz on 02,Февраль,2021
 */

class CashierHistoryAdapter :
    ListAdapter<CashierHistoryData, CashierHistoryAdapter.VHolder>(CashierHistoryData.ITEMCALLBACK) {

    var listener: SingleBlock<CashierHistoryData>? = null

    fun setOnClickListener(block: SingleBlock<CashierHistoryData>) {
        listener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        ItemHistoryChashierBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.bind()

    inner class VHolder(val binding: ItemHistoryChashierBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }

        fun bind() = bindItem {

            val d = currentList[adapterPosition]
            binding.apply {
                numberTable.text = d.id.toString()
                orderPrice.text = d.orderPrice
                payType.text = d.orderType
                cashBack.text = d.cashBack
            }
        }
    }
}