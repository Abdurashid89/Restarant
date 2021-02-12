package com.example.restuarant.ui.waiter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.CategoryItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.CategoryItemData
import com.squareup.picasso.Picasso

class CategoryItemAdapter :
    ListAdapter<CategoryItemData, CategoryItemAdapter.Vh>(CategoryItemData.ITEM_CALLBACK) {

    private var listener: SingleBlock<CategoryItemData>? = null

    fun setOnClickListener(data: (CategoryItemData) -> Unit) {
        listener = data
    }


    inner class Vh(val view: CategoryItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind() {
            itemView.apply {
                val d = currentList[adapterPosition]
                view.productName.text = d.name
                view.productName.isSelected = true
                view.productPhoto.setImageResource(R.drawable.apple)
                Picasso.get().load(d.photoId).into(view.productPhoto)
            }

        }

        init {
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