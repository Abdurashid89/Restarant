package com.example.restuarant.ui.wareHouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemEnterProductBinding
import com.example.restuarant.databinding.ItemWareHouseBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.model.entities.ProductInData

/**
 * Created by Davronbek on 10,Февраль,2021
 */
class EnterProductAdapter :
    RecyclerView.Adapter<EnterProductAdapter.VH>() {
    private var listener: SingleBlock<ProductInData>? = null
    private var list = ArrayList<ProductInData>()
    fun setOnClickListener(block: SingleBlock<ProductInData>) {
        listener = block
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun submitList(ls: List<ProductInData>) {
        list.clear()
        list.addAll(ls)
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemEnterProductBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = list[adapterPosition]
            binding.apply {
                productName.text = d.name

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemEnterProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = list.size
}