package com.example.restuarant.ui.cashier.check

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import com.example.restuarant.databinding.CheckLayoutBinding
import com.example.restuarant.databinding.ItemCookerBinding
import timber.log.Timber

/**
 * Created by Shohboz Qoraboyev on 19,Февраль,2021
 */

class CookerCheckDialog(context: Context, list:List<CookerCheckData>) :
    AlertDialog(context) {

    var _bn: ItemCookerBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")


    init {
        _bn = ItemCookerBinding.inflate(LayoutInflater.from(context), null, false)
        setView(_bn!!.root)

        bn.rv.setOnClickListener { dismiss() }


    }


}