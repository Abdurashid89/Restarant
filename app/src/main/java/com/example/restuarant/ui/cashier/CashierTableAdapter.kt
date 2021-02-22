package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.R
import com.example.restuarant.databinding.DeskItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.bindItem
import com.example.restuarant.extentions.sortTable
import com.example.restuarant.model.entities.TableData

/**
 * Created by shohboz on 21,Январь,2021
 */
class CashierTableAdapter : RecyclerView.Adapter<CashierTableAdapter.CashierTableHolder>() {
    val tableList = ArrayList<TableData>()

    var tableIndex = 0
    var listener: SingleBlock<TableData>? = null

    fun submitList(list: ArrayList<TableData>) {
        tableList.clear()
        tableList.addAll(list.sortTable())
        notifyDataSetChanged()
    }

    fun setOnClickListener(block: SingleBlock<TableData>) {
        listener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CashierTableHolder(
        DeskItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CashierTableAdapter.CashierTableHolder, position: Int) =
        holder.bind()

    override fun getItemCount() = tableList.size

    inner class CashierTableHolder(val binding: DeskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                notifyItemChanged(tableIndex)
                itemView.alpha = 0.5f
                tableIndex = adapterPosition
                listener?.invoke(tableList[adapterPosition])
                tableIndex = adapterPosition
            }
        }

        @SuppressLint("Range", "ResourceAsColor")
        fun bind() = bindItem {
            val d = tableList[adapterPosition]
            binding.tableText.text = tableList[adapterPosition].name.toString()
            binding.linearTable.setBackgroundResource(if (!d.active) R.color.red else R.color.green)
        }
    }

}


