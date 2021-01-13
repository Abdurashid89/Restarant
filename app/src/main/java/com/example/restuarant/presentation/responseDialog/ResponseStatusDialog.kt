package com.example.restuarant.presentation.responseDialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import com.example.restuarant.R
import com.example.restuarant.databinding.LayoutDialogBinding

class ResponseStatusDialog(context: Context, message: String, status: Boolean) :
    AlertDialog(context) {
    private lateinit var binding: LayoutDialogBinding


    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.layout_dialog, null, false)

    init {
        setView(contentView)
        binding = LayoutDialogBinding.bind(contentView)
        if (status) {
            binding.status.text = "Success"
            binding.status.setTextColor(Color.parseColor("#107A15"))
            binding.description.text = message
            binding.image.setImageResource(R.drawable.ic_baseline_check_circle_24)
        } else {
            binding.status.text = "Error"
            binding.status.setTextColor(Color.parseColor("#B30843"))
            binding.description.text = message
            binding.image.setImageResource(R.drawable.ic_baseline_cancel_presentation_24)
        }
        binding.btnOk.setOnClickListener { dismiss() }

    }
}