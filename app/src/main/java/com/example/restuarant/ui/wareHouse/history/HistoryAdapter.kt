package com.example.restuarant.ui.wareHouse.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemProductHistoryBinding
import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.extentions.customLog
import com.example.restuarant.model.entities.ProductHistoryData
import com.example.restuarant.ui.wareHouse.search.ProductHistoryCallBack
import com.example.restuarant.ui.wareHouse.search.ProductHistorySearch

/**
 * Created by Davronbek on 17,Февраль,2021
 */
class HistoryAdapter(val type: Boolean) :
    RecyclerView.Adapter<HistoryAdapter.VH>() {
    val list = ArrayList<ProductHistoryData>()
    var search: ITextWatcher

    init {
        search = ProductHistorySearch(this, list)
    }

    var listener: SingleBlock<ProductHistoryData>? = null
    var listenerTextNotFound: SingleBlock<Boolean>? = null

    fun submitList(productHistoryList: List<ProductHistoryData>) {
        list.clear()
        list.addAll(productHistoryList)
        search = ProductHistorySearch(this, list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(block: SingleBlock<ProductHistoryData>) {
        listener = block
    }

    fun onSearch(text: String) {
        search.onTextChanged(text)
    }

    inner class VH(val binding: ItemProductHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = list[adapterPosition]
            binding.apply {
//                productId.text = d.id.toString()
                productName.text = d.name
                productCount.text = d.presentCount.toString()
                if (type) {
                    customLog(d.name + "incomePrice->" + d.incomePrice.toString())
                    productOnePrice.text = d.incomePrice.toString()
                    productAllSum.text = "${d.incomePrice * d.presentCount}"
                } else {
                    if (d.sold) {
                        productOnePrice.text = "----"
                        productAllSum.text = "----"
                    } else {
                        productOnePrice.text = d.sellPrice.toString()
                        productAllSum.text = "${d.sellPrice * d.presentCount}"
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemProductHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()


    fun updateProductHistories(searchedUser: ArrayList<ProductHistoryData>) {
        if (searchedUser.isEmpty()) {
            listenerTextNotFound?.invoke(false)
            list.clear()
            notifyDataSetChanged()
        } else {
            listenerTextNotFound?.invoke(true)
            val callBack = ProductHistoryCallBack(searchedUser, list)
            val result = DiffUtil.calculateDiff(callBack)
            list.clear()
            list.addAll(searchedUser)
            result.dispatchUpdatesTo(this)
            notifyDataSetChanged()
        }

    }

    fun setOnFoundListener(block: SingleBlock<Boolean>) {
        listenerTextNotFound = block
    }

    override fun getItemCount() = list.size

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}