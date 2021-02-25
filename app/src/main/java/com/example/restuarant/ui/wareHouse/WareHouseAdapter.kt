package com.example.restuarant.ui.wareHouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.ItemWareHouseBinding
import com.example.restuarant.extentions.ITextWatcher
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.extentions.search.Search
import com.example.restuarant.model.entities.ProductInData
import com.example.restuarant.ui.wareHouse.search.ProductInCallBack
import com.example.restuarant.ui.wareHouse.search.ProductInSearch

class WareHouseAdapter :
    RecyclerView.Adapter<WareHouseAdapter.VH>() {
    var search: ITextWatcher? = null

    val list = ArrayList<ProductInData>()

    var listener: SingleBlock<ProductInData>? = null

    init {
        search = ProductInSearch(this, list)
    }

    fun submitList(productList: List<ProductInData>) {
        list.clear()
        list.addAll(productList)
        search = ProductInSearch(this, list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(block: SingleBlock<ProductInData>) {
        listener = block
    }

    fun onSearch(text: String) {
        search!!.onTextChanged(text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemWareHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()


    fun updateProducts(searchedUser: ArrayList<ProductInData>) {
        val callBack = ProductInCallBack(searchedUser, list)
        val result = DiffUtil.calculateDiff(callBack)
        list.clear()
        list.addAll(searchedUser)
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemWareHouseBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(list[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val d = list[adapterPosition]
            binding.apply {
                productId.text = d.id.toString()
                productName.text = d.name
                productWeight.text = d.presentCount.toString()
                productUnit.text = d.type
            }
        }
    }

    override fun getItemCount() = list.size
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

}