package com.example.restuarant.ui.cashier

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.CashOpenExitLayoutBinding
import com.example.restuarant.extentions.SingleBlock
import com.example.restuarant.extentions.vibrate
import java.lang.NullPointerException

/**
 * Created by shohboz on 27,Январь,2021
 */
class CashOpenExitDialog(context: Context,string: String) : AlertDialog(context){
    var _bn:CashOpenExitLayoutBinding? = null
    val bn get() = _bn ?: throw NullPointerException("error")
    var listener:SingleBlock<String>? = null

    init {
        _bn = CashOpenExitLayoutBinding.inflate(LayoutInflater.from(context),null, false)
        setView(_bn?.root)
        bn.openExitCash.text = string

        bn.btnSend.setOnClickListener {
            val price = bn.inputPrice.text.toString().trim()
            when{
                price.isEmpty() ->{
                    bn.inputPrice.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                    vibrate(context)
                    return@setOnClickListener
                }
                price.length < 4 ->{
                    bn.inputPrice.error = "Very little amount"
                    return@setOnClickListener
                }
                else ->{
                    listener?.invoke(price)
                }
            }
        }

    }

    fun setOnClickListener(block: SingleBlock<String>){
        listener = block
    }

}