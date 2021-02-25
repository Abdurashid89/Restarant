package com.example.restuarant.ui.cashier

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.restuarant.R
import com.example.restuarant.databinding.DeskItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.TableData

/**
 * # Created by Elyor on 18,February,2021 #
 */
//class CashierTableAdapter3: RecyclerView.Adapter<CashierTableAdapter3.CashierTableHolder>() {
//    val tableList = ArrayList<TableData>()
//
//    var listener: SingleBlock<TableData>? = null
//
//    fun submitList(list: List<TableData>) {
//        tableList.clear()
//        tableList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    fun setOnClickListener(block: SingleBlock<TableData>) {
//        listener = block
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CashierTableHolder(
//        DeskItemBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        )
//    )
//
//    override fun onBindViewHolder(holder: CashierTableAdapter3.CashierTableHolder, position: Int) =
//        holder.bind()
//
//    override fun getItemCount() = tableList.size
//
//    inner class CashierTableHolder(val binding: DeskItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            itemView.setOnClickListener {
//                listener?.invoke(tableList[adapterPosition])
//            }
//        }
//
//        @SuppressLint("Range", "ResourceAsColor")
//        fun bind() = bindItem {
//            val d = tableList[adapterPosition]
//            binding.tableText.text = tableList[adapterPosition].name.toString()
//            binding.linearTable.setBackgroundResource(if (!d.active) R.color.red else R.color.green)
//        }
//    }
//}

class CashierTableAdapter3 : RecyclerView.Adapter<CashierTableAdapter3.CashierTableHolder>() {

    private var listener: SingleBlock<TableData>? = null

    private val callBack = object : SortedListAdapterCallback<TableData>(this) {
        override fun areItemsTheSame(item1: TableData?, item2: TableData?) =
            item1!!.id == item2!!.id

        override fun compare(o1: TableData?, o2: TableData?): Int {
            return when {
                o1!!.id > o2!!.id -> 1
                o1.id == o2.id -> 0
                else -> -1
            }
        }

        override fun areContentsTheSame(oldItem: TableData?, newItem: TableData?) =
            oldItem!!.name == newItem!!.name && oldItem.active == newItem.active
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashierTableHolder {
        return CashierTableHolder(DeskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = sortedList.size()

    override fun onBindViewHolder(holder: CashierTableHolder, position: Int) = holder.onBind()

    inner class CashierTableHolder(private val view: DeskItemBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(sortedList[adapterPosition])
            }
        }

        @SuppressLint("Range", "ResourceAsColor")
        fun onBind(){
            val d = sortedList[adapterPosition]
            view.tableText.text = sortedList[adapterPosition].name.toString()
            view.linearTable.setBackgroundResource(if (!d.active) R.color.red else R.color.green)
        }
    }
}
