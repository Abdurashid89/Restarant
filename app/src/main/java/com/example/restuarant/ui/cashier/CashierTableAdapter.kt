package com.example.restuarant.ui.cashier

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

    fun setOnClickListener(block: SingleBlock<CashierTableData>){
        listener = block
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(DeskItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: VHolder, position: Int)  = holder.bind()

    inner class VHolder(val binding:DeskItemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }
        fun bind() = bindItem{
            binding.tableText.text = currentList[adapterPosition].id.toString()
        }
    }
}