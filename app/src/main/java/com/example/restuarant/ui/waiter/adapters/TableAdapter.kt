package com.example.restuarant.ui.waiter.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.restuarant.databinding.TableItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.TableData

class TableAdapter :RecyclerView.Adapter<TableAdapter.VHolder>(){

    private var listener: SingleBlock<TableData>? = null

    private val callBack = object : SortedListAdapterCallback<TableData>(this) {
        override fun areItemsTheSame(item1: TableData?, item2: TableData?) = item1!!.id == item2!!.id
        override fun compare(o1: TableData?, o2: TableData?): Int {
            return when {
                o1!!.id > o2!!.id -> 1
                o1.id == o2.id -> 0
                else -> -1
            }
        }
        override fun areContentsTheSame(oldItem: TableData?, newItem: TableData?) = oldItem!!.name == newItem!!.name && oldItem.active == newItem.active
    }

    private val sortedList = SortedList(TableData::class.java, callBack)

    fun setOnClickListener(block: SingleBlock<TableData>) {
        listener = block
    }

    fun submitList(data: List<TableData>) {
        sortedList.beginBatchedUpdates()
        sortedList.replaceAll(data)
        sortedList.endBatchedUpdates()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(TableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = sortedList.size()

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.onBind()

    inner class VHolder(private val view: TableItemBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(sortedList[adapterPosition])
            }
        }
        @SuppressLint("Range", "ResourceAsColor")
        fun onBind() {
            val d = sortedList[adapterPosition]
            if (d.active){
                view.tablePageLinear.setBackgroundResource(android.R.color.holo_green_dark)
            }else{
                view.tablePageLinear.setBackgroundResource(android.R.color.holo_red_dark)
            }
//            val name = d.name.substring(d.name.length - 2, d.name.length)
//            view.tablePageNumberTv.text = name.replace("-","")
            view.tablePageNumberTv.text = d.id.toString()
        }
    }
}
