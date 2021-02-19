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

class CookerCheckDialog(context: Context, uri:Uri) :
    AlertDialog(context) {

    var _bn: ItemCookerBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")


    init {
        _bn = ItemCookerBinding.inflate(LayoutInflater.from(context), null, false)
        setView(_bn!!.root)

        bn.pdfWiew.fromUri(uri).defaultPage(0)
            .spacing(30)
            .onRender { _, _, _ -> bn.pdfWiew.fitToWidth() }
            .onLoad { bn.pdfWiew.zoomTo(bn.pdfWiew.width.toFloat()/bn.pdfWiew.optimalPageWidth)
            bn.pdfWiew.moveTo(0f,0f)}
            .load()
        bn.uri.text = uri.toString()

    }


}