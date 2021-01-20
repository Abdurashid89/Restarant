package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.CategoryItemBinding
import com.example.restuarant.model.entities.CategoryData

class CategoryAdapter:ListAdapter<CategoryData,CategoryAdapter.Vh>(CategoryData.ITEM_CALLBACK) {

    inner class Vh(val view: CategoryItemBinding):RecyclerView.ViewHolder(view.root){
        fun onBind(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()

}