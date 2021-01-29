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

    private var listener:SingleBlock<WaiterOrderData>? = null
    fun setOnClickListener(onClick:SingleBlock<WaiterOrderData>){
        listener = onClick
    }

    inner class Vh(val view:OrderItemBinding):RecyclerView.ViewHolder(view.root){

        init {
            view.orderPlus.setOnClickListener {
                minusListener?.invoke(getItem(adapterPosition))
            }
            view.orderMinus.setOnClickListener {
                plusListener?.invoke(currentList[adapterPosition])
            }
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }

        fun onBind(){
            itemView.apply {
                val w = currentList[adapterPosition]
                view.productNameTv.text = w.productName
                view.productPriceTv.text = w.productPrice.toString()
                view.productCountTv.text = w.productCount.toString()
            }


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
        data.productTotalPrice*data.productCount
    }
    fun minus(data: WaiterOrderData){
        if (data.productCount!=0){
            data.productCount-1
        }
        data.productTotalPrice*data.productCount
    }
    fun addProduct(data: CategoryData){
        val list = currentList.toMutableList()
//        val waiterOrderData = WaiterOrderData()
//        list.add(waiterOrderData)

    }

}