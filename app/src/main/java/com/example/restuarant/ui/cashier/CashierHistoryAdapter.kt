package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.ItemCookerOrderBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.OrderGetData
import com.example.restuarant.ui.cashier.check.CookerCheckData

/**
 * Created by Abdurashid on 08,Февраль,2021
 */

class CashierHistoryAdapter : RecyclerView.Adapter<CashierHistoryAdapter.HistoryViewHolder>() {
    val list = ArrayList<CookerCheckData>()

    fun submitList(ls: CookerCheckData) {
        list.clear()
        list.add(ls)
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

        @SuppressLint("ResourceAsColor")
        fun bind() = bindItem {

            val d = list[adapterPosition]
            itemView.apply {
              binding.apply {
                  mealName.text = d.name
                  olderCount.text = d.older
                  plusMinus.text = d.plus_minus
                   if(d.latest == ""){ viewGroup.setBackgroundColor(R.color.light_black)
                  latestCount.text = d.latest
              } else  latestCount.text = d.latest
              }
            }
        }
    }
}



