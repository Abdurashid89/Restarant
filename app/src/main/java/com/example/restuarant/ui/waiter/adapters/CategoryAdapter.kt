package com.example.restuarant.ui.waiter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.CategoryItem2Binding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.CategoryData
import com.squareup.picasso.Picasso

class CategoryAdapter : ListAdapter<CategoryData, CategoryAdapter.Vh>(CategoryData.ITEM_CALLBACK) {


    private var listener:SingleBlock<CategoryData>? = null

    fun setOnClickListener(data:SingleBlock<CategoryData>){
        listener = data
    }

    inner class Vh(val view: CategoryItem2Binding) : RecyclerView.ViewHolder(view.root) {

        init {
            itemView.setOnClickListener {
                listener?.invoke(getItem(adapterPosition))
            }
        }

        fun onBind() {
            val categoryData = currentList[adapterPosition]
            view.productName.text = categoryData.name
            view.productName.isSelected = true
            Picasso.get().load(categoryData.photoURL).into(view.productPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()

}