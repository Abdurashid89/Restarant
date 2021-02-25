package com.example.restuarant.ui.wareHouse.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemProductHistoryBinding
import com.example.restuarant.extentions.*
import com.example.restuarant.model.entities.ProductHistoryData
import com.example.restuarant.model.entities.ProductInData
import kotlin.random.Random

/**
 * Created by Davronbek on 17,Февраль,2021
 */
class HistoryAdapter(val type: Boolean) :
    ListAdapter<ProductHistoryData, HistoryAdapter.VH>(ProductHistoryData.ITEM_CALLBACK) {

    var listener: SingleBlock<ProductHistoryData>? = null

    fun setOnClickListener(block: SingleBlock<ProductHistoryData>) {
        listener = block
    }


    inner class VH(val binding: ItemProductHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = currentList[adapterPosition]
            binding.apply {
                productId.text = Random.nextInt(1,100).toString()
                productName.text = d.name
                productUnit.text = d.updatedAt.substring(0, 10)
                productHours.text = d.updatedAt.substring(11, 16)
                productCount.text = d.presentCount.formatDouble()
                if (type) {
                    customLog(d.name + "incomePrice->" + d.incomePrice.toString())
                    productOnePrice.text = d.incomePrice.formatDouble()
                    productAllSum.text = (d.incomePrice * d.presentCount).formatDouble()
                } else {
                    if (d.sold) {
                        productOnePrice.text = "----"
                        productAllSum.text = "----"
                    } else {
                        productOnePrice.text = d.sellPrice.formatDouble()
                        productAllSum.text = (d.sellPrice * d.presentCount).formatDouble()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemProductHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
}