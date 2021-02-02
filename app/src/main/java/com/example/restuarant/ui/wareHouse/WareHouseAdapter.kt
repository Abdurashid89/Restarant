package com.example.restuarant.ui.wareHouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemWareHouseBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.ProductData
import com.example.restuarant.model.entities.ProductInData

class WareHouseAdapter :
    ListAdapter<ProductInData, WareHouseAdapter.VH>(ProductInData.ITEM_CALLBACK) {

    var listener: SingleBlock<ProductInData>? = null

    fun setOnClickListener(block: SingleBlock<ProductInData>) {
        listener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemWareHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    inner class VH(val binding: ItemWareHouseBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = currentList[adapterPosition]
            binding.apply {
                productId.text = d.id.toString()
                productName.text = d.name
                productWeight.text = d.createdAt
            }
        }
    }

}