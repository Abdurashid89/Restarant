package com.example.restuarant.ui.wareHouse.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemProductHistoryBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData

/**
 * Created by Davronbek on 17,Февраль,2021
 */
class HistoryAdapter(val type: Boolean) :
    ListAdapter<ProductData, HistoryAdapter.VH>(ProductData.ITEM_CALLBACK) {

    var listener: SingleBlock<ProductData>? = null

    fun setOnClickListener(block: SingleBlock<ProductData>) {
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
//                productId.text = d.id.toString()
                productName.text = d.name
                productCount.text = d.presentCount.toString()
                if (type) {
                    productOnePrice.text = d.incomePrice.toString()
                    productAllSum.text = "${d.incomePrice * d.presentCount}"
                } else {
                    if (!d.sold) {
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
}