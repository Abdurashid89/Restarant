package com.example.restuarant.ui.cashier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemOrderCashierBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.CashierOrderData

/**
 * Created by shohboz on 21,Январь,2021
 */
class CashierOrderAdapter :
    RecyclerView.Adapter<CashierOrderAdapter.VHolder>() {

    private val list = ArrayList<CashierOrderData>()

    val getOrderList get() = list


    var listener: SingleBlock<CashierOrderData>? = null

    fun setOnClickListener(block: SingleBlock<CashierOrderData>) {
        listener = block
    }

    fun submitList(orderList: ArrayList<CashierOrderData>) {
        list.clear()
        list.addAll(orderList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        ItemOrderCashierBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.bind()

    inner class VHolder(val binding: ItemOrderCashierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {

            val d = list[adapterPosition]
            binding.apply {
                mealName.text = d.mealName
                mealPrice.text = d.price.toString()
                mealCount.text = d.count.toString()
                total.text = d.total
            }
        }
    }

    override fun getItemCount() = list.size
}