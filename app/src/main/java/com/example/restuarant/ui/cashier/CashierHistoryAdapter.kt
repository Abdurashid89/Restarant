package com.example.restuarant.ui.cashier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemHistoryChashierBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.CashierHistoryData

/**
 * Created by Abdurashid on 08,Февраль,2021
 */

class CashierHistoryAdapter : RecyclerView.Adapter<CashierHistoryAdapter.HistoryViewHolder>() {
    val list = ArrayList<CashierHistoryData>()

    fun submitList(ls: List<CashierHistoryData>) {
        list.clear()
        list.addAll(ls)
        notifyDataSetChanged()
    }

    var listener: SingleBlock<CashierHistoryData>? = null

    fun setOnClickListener(block: SingleBlock<CashierHistoryData>) {
        listener = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemHistoryChashierBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = list.size

    inner class HistoryViewHolder(val binding: ItemHistoryChashierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {

            val d = list[adapterPosition]
            binding.apply {
                numberTable.text = d.id.toString()
                orderPrice.text = d.orderPrice
                payType.text = d.orderType
                cashBack.text = d.cashBack
            }
        }
    }
}



