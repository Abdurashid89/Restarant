package com.example.restuarant.ui.global

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by shohboz on 27,Январь,2021
 */
interface BaseWatcher : TextWatcher{
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}