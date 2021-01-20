package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R

class DeskAdapter(val deskList: List<Int>,val listener:OnDeskItemClickListener):RecyclerView.Adapter<DeskAdapter.Vh>(){

    inner class Vh(itemView: View):RecyclerView.ViewHolder(itemView){
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
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.desk_item,parent,false))
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