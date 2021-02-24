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

class CookerCheckDialog(context: Context, tableId: Long, list:List<CookerCheckData>) :
    AlertDialog(context) {

    var _bn: ItemCookerBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    val adapter = CookerCheckAdapter()


    init {
        _bn = ItemCookerBinding.inflate(LayoutInflater.from(context), null, false)
        setView(_bn!!.root)

        bn.rv.adapter = adapter
        adapter.submitList(list)

        bn.tableNumber.text = tableId.toString()

        bn.group.setOnClickListener {
            dismiss()
            _bn = null
        }

    }


}