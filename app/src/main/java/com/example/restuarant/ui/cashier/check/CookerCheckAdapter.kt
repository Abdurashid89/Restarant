package com.example.restuarant.ui.cashier.check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.ItemCookerOrderBinding
import com.example.restuarant.databinding.ItemHistoryChashierBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.ui.cashier.CashierHistoryAdapter

/**
 * Created by Shohboz Qoraboyev on 19,Февраль,2021
 */

class CookerCheckAdapter : RecyclerView.Adapter<CookerCheckAdapter.HistoryViewHolder>() {
    val list = ArrayList<CookerCheckData>()

    fun submitList(ls: List<CookerCheckData>) {
        list.clear()
        list.addAll(ls)
        notifyDataSetChanged()
    }

    var listener: SingleBlock<CookerCheckData>? = null

    fun setOnClickListener(block: SingleBlock<CookerCheckData>) {
        listener = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemCookerOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = list.size

    inner class HistoryViewHolder(val binding: ItemCookerOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = list[adapterPosition]
            binding.apply {
              mealName.text = d.name
              plusMinus.text = d.latest
            }
        }
    }
}