package com.example.restuarant.ui.cashier.check

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restuarant.databinding.ItemCookerBinding
import com.example.restuarant.extentions.customLog

/**
 * Created by Shohboz Qoraboyev on 19,Февраль,2021
 */

class CookerCheckDialog(context: Context, val name: Int, val list: ArrayList<CookerCheckData>) :
    AlertDialog(context) {

    var _bn: ItemCookerBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")
    val adapter = CookerCheckAdapter()

    init {
        _bn = ItemCookerBinding.inflate(LayoutInflater.from(context), null, false)
        setView(bn.root)

        bn.rv.adapter = adapter
        customLog("InAdapterSize---->${list.size}")
        adapter.submitList(list)

        bn.tableNumber.text = name.toString()

        bn.group.setOnClickListener {
            dismiss()
            _bn = null

        }
    }
}
