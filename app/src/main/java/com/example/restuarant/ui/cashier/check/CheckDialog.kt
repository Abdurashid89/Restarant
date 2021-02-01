package com.example.restuarant.ui.cashier.check

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.restuarant.databinding.CheckLayoutBinding
import com.example.restuarant.extentions.SingleBlock
import timber.log.Timber

/**
 * Created by shohboz on 28,Январь,2021
 */

class CheckDialog(context: Context, data: String, nime: String, encoding: String) :
    AlertDialog(context) {

    var _bn: CheckLayoutBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    var listener: SingleBlock<String>? = null

    init {
        _bn = CheckLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        setView(_bn!!.root)

        Timber.d(data)
        Timber.d(nime)
        Timber.d(encoding)
        bn.webView.loadData(data, nime, encoding)
        bn.btnOk.setOnClickListener {
            listener?.invoke("Success")
        }

    }

    fun setOnClickListener(block: SingleBlock<String>) {
        listener = block
    }

}