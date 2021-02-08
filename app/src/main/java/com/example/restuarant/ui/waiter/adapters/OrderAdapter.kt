package com.example.restuarant.ui.waiter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.OrderItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.WaiterOrderData

class OrderAdapter : ListAdapter<WaiterOrderData, OrderAdapter.Vh>(WaiterOrderData.ITEM_CALLBACK) {

    private var plusListener: SingleBlock<Int>? = null
    private var minusListener: SingleBlock<Int>? = null
    private var deleteListener: SingleBlock<Int>? = null
    private var listener: SingleBlock<WaiterOrderData>? = null

    fun setOnPlusClickListener(plus: SingleBlock<Int>) {
        plusListener = plus
    }


    fun setOnMinusClickListener(minus: SingleBlock<Int>) {
        minusListener = minus
    }


    fun setOnDeleteClickListener(delete: SingleBlock<Int>) {
        deleteListener = delete
    }


    fun setOnClickListener(onClick: SingleBlock<WaiterOrderData>) {
        listener = onClick
    }

    inner class Vh(val view: OrderItemBinding) : RecyclerView.ViewHolder(view.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
            view.orderPlus.setOnClickListener {
                plusListener?.invoke(adapterPosition)
            }
            view.orderMinus.setOnClickListener {
                minusListener?.invoke(adapterPosition)
            }
        }

        fun onBind() {
            itemView.apply {
                val w = currentList[adapterPosition]
                view.productNameTv.text = w.productName
                view.productPriceTv.text = w.productPrice.toString()
                view.productCountTv.text = w.productCount.toString()
                view.totalPriceTv.text = w.productTotalPrice.toString()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()

    fun plus(index: Int) {
        currentList[index].productCount++
        currentList[index].productTotalPrice =
            (currentList[index].productPrice * currentList[index].productCount)
        notifyItemChanged(index)
    }

    fun minus(index: Int) {
        val d = currentList[index]
        if (d.productCount != 1) {
            d.productCount--
            d.productTotalPrice =
                (d.productPrice * d.productCount)
            notifyItemChanged(index)
        } else {
            val ls = currentList.toMutableList()
            ls.removeAt(index)
            submitList(ls)
        }
    }

    fun addProduct(data: WaiterOrderData) {
        val list = currentList.toMutableList()
        list.add(data)
        submitList(list)
    }

}