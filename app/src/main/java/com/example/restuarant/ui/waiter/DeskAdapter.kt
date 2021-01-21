package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.DeskItemBinding

class DeskAdapter(val deskList: List<Int>,val listener:OnDeskItemClickListener):RecyclerView.Adapter<DeskAdapter.Vh>(){

    inner class Vh(val view:DeskItemBinding):RecyclerView.ViewHolder(view.root){
        fun onBind(desk: Int) {

            itemView.setOnClickListener{
                listener.onClick()
            }
            itemView.setOnLongClickListener {
            listener.onLongClick()
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(DeskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(deskList[position])
    }

    override fun getItemCount(): Int = deskList.size

    interface OnDeskItemClickListener{
        fun onClick()
        fun onLongClick()
    }

}