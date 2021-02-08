package com.example.restuarant.ui.wareHouse.product

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.restuarant.R
import com.example.restuarant.model.entities.ProductInData

class ProductItemDialog(context: Context, item: ProductInData) : AlertDialog(context) {

    @SuppressLint("InflateParams")
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.item_product, null, false)

    init {
        setView(contentView)
//        val view = ItemProductItemBinding.bind(contentView)
//        view.categoryName.text = item.category.name
    /*    view.productName.text = item.name
        view.productDate.text = item.createdAt.substring(0, 16).replace("T", " ")
        view.btnOk.setOnClickListener { dismiss() }*/
    }
}