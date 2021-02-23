package com.example.restuarant.extentions

import android.text.Editable
import android.text.TextWatcher

class CustomWatcher(val listener: ITextWatcher) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//        listener.onTextChanged(p0.toString())
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        listener.onTextChanged(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
//        listener.onTextChanged(p0.toString())
    }
}