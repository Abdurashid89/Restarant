package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.CategoryItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.CategoryData

class GoodsCategoryAdapter :
    ListAdapter<CategoryData, GoodsCategoryAdapter.Vh>(CategoryData.ITEM_CALLBACK) {

    private var listener: SingleBlock<CategoryData>? = null
    fun setOnClickListener(block: (CategoryData) -> Unit) {
        listener = block
    }

    private var delete: SingleBlock<CategoryData>? = null
    fun setOnDeleteClickListener(block: (CategoryData) -> Unit) {
        delete = block
    }

    fun deleteItem(data: CategoryData) {
        val ls = currentList.toMutableList()
        ls.remove(data)
        submitList(ls)
    }

    inner class Vh(val view: CategoryItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind() {
            itemView.apply {
                val d = currentList[adapterPosition]
                view.productName.text = d.name
            }

        }

        init {
            view.productPhoto.setOnClickListener {
                delete?.invoke(currentList[adapterPosition])
            }
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()


}