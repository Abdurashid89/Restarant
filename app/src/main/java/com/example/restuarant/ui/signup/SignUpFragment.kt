package com.example.restuarant.ui.signup

import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.presentation.signup.SignUpView
import com.example.restuarant.ui.global.BaseFragment

class SignUpFragment : BaseFragment(),SignUpView {
    override val layoutRes: Int = R.layout.fragment_signup

    private lateinit var binding : FragmentSignupBinding

    override fun showMessage(message: String) {

    }

    override fun makeLoadingVisible(status: Boolean) {

    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }
}