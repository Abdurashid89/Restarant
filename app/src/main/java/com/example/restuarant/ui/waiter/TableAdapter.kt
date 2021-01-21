package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.TableItemBinding
import com.example.restuarant.model.entities.TableData

class TableAdapter:ListAdapter<TableData,TableAdapter.Vh>(TableData.ITEM_CALLBACK) {

    private var listener:((TableData)->Unit)? = null

    fun setOnClickListener(data: (TableData)->Unit){
        listener = data
    }

    inner class Vh(val view:TableItemBinding):RecyclerView.ViewHolder(view.root){

        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }

        fun onBind(){
            view.tablePageNumberTv.text = getItem(adapterPosition).number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(TableItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()
}