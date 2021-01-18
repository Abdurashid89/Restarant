package com.example.restuarant.ui.wareHouse.product

import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentProductBinding
import com.example.restuarant.ui.global.BaseFragment

class ProductFragment() : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_product

    private lateinit var binding: FragmentProductBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        binding
    }
}