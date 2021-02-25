package com.example.restuarant.ui.cashier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemHistoryChashierBinding
import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.extentions.search.OrderCallBack
import com.example.restuarant.extentions.search.Search
import com.example.restuarant.model.entities.OrderGetData

/**
 * Created by Abdurashid on 08,Февраль,2021
 */

class CashierHistoryAdapter : RecyclerView.Adapter<CashierHistoryAdapter.HistoryViewHolder>() {
    private val list = ArrayList<OrderGetData>()
    var search: ITextWatcher

    init {
        search = Search(this, list)
    }

    fun submitList(ls: List<OrderGetData>) {
        list.clear()
        list.addAll(ls)
        search = Search(this, list)

        notifyDataSetChanged()
    }

    fun getAllHistory() = list

    var listener: SingleBlock<OrderGetData>? = null

    fun setOnClickListener(block: SingleBlock<OrderGetData>) {
        listener = block
    }

    fun onSearch(text: String) {
        search.onTextChanged(text)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemHistoryChashierBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = list.size

    fun updateOrders(searchedUser: ArrayList<OrderGetData>) {
        val callBack = OrderCallBack(searchedUser, list)
        val result = DiffUtil.calculateDiff(callBack)
        list.clear()
        list.addAll(searchedUser)
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(val binding: ItemHistoryChashierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.orderDetail.setOnClickListener { listener!!.invoke(list[adapterPosition]) }
        }

        fun bind() = bindItem {


            val d = list[adapterPosition]
            binding.apply {
                tvTransaction.text = d.id.toString()
                numberTable.text = d.table.id.toString()
                orderPrice.text = d.orderPrice.toString()
                payType.text = d.payType
                cashBack.text = d.cashBack.toString()
            }
        }
    }
}



