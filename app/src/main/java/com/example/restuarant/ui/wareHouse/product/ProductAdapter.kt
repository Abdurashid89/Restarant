package com.example.restuarant.ui.wareHouse.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ShowItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.ProductInData

class ProductAdapter : ListAdapter<ProductInData, ProductAdapter.VH>(ProductInData.ITEM_CALLBACK) {
    private var listener: SingleBlock<ProductInData>? = null

    fun setOnClickListener(block: SingleBlock<ProductInData>) {
        listener = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShowItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    inner class VH(val binding: ShowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                binding.radioButton.isChecked = true
                listener?.invoke(getItem(adapterPosition))
            }
        }

        fun bind() = bindItem {
            binding.itemNameText.text = currentList[adapterPosition].name
        }
    }
}