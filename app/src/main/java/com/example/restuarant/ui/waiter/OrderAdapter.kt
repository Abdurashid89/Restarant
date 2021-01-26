package com.example.restuarant.ui.waiter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restuarant.databinding.OrderItemBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.model.entities.CategoryData
import com.example.restuarant.model.entities.WaiterOrderData

class OrderAdapter :ListAdapter<WaiterOrderData,OrderAdapter.Vh>(WaiterOrderData.ITEM_CALLBACK){

    private var plusListener:SingleBlock<WaiterOrderData>? = null
    fun setOnPlusClickListener(plus: SingleBlock<WaiterOrderData>){
        plusListener = plus
    }
    private var minusListener:SingleBlock<WaiterOrderData>? = null
    fun setOnMinusClickListener(minus:SingleBlock<WaiterOrderData>){
        minusListener = minus
    }
    private var deleteListener:SingleBlock<WaiterOrderData>? = null
    fun setOnDeleteClickListener(delete:SingleBlock<WaiterOrderData>){
        deleteListener = delete
    }

    inner class Vh(val view:OrderItemBinding):RecyclerView.ViewHolder(view.root){

        init {
            view.orderPlus.setOnClickListener {
                minusListener?.invoke(getItem(adapterPosition))
            }
            view.orderMinus.setOnClickListener {
                plusListener?.invoke(currentList[adapterPosition])
            }

        }

        fun onBind(){
            view.productNameTv.text = getItem(adapterPosition).productName
            view.productPriceTv.text = getItem(adapterPosition).productPrice.toString()
            view.productCountTv.text = getItem(adapterPosition).productCount.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.onBind()

    fun plus(data: WaiterOrderData){
        data.productCount+1
        val i = data.productPrice * data.productCount
        data.productTotalPrice+=i

    }
    fun minus(data: WaiterOrderData){
        if (data.productCount!=0){
            data.productCount-1
        }
    }

}