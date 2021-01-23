package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.DeskItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.extentions.inflate
import com.example.restuarant.model.entities.CashierTableData

/**
 * Created by shohboz on 21,Январь,2021
 */
class CashierTableAdapter:ListAdapter<CashierTableData,CashierTableAdapter.VHolder>(CashierTableData.ITEMCALLBACK){

    var listener:SingleBlock<CashierTableData>? = null
    var tableIndex = 0

    fun setOnClickListener(block: SingleBlock<CashierTableData>){
        listener = block
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(DeskItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: VHolder, position: Int)  = holder.bind()

    inner class VHolder(val binding:DeskItemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                currentList[tableIndex].color = "#F42B4A"
                notifyItemChanged(tableIndex)
                itemView.alpha = 0.5f
                tableIndex = adapterPosition
                listener?.invoke(currentList[adapterPosition])
                tableIndex = adapterPosition
            }
        }
        @SuppressLint("Range")
        fun bind() = bindItem{
            binding.tableText.text = currentList[adapterPosition].id.toString()
            binding.groupDesc.setCardBackgroundColor(Color.parseColor(currentList[adapterPosition].color))
        }
    }
}