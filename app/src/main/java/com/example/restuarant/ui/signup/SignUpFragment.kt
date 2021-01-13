package com.example.restuarant.ui.signup

import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.presentation.signup.SignUpView
import com.example.restuarant.presentation.signup.SignupPresenter
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SignUpFragment : BaseFragment(),SignUpView {
    override val layoutRes: Int = R.layout.fragment_signup

    private lateinit var binding : FragmentSignupBinding

    @InjectPresenter
    lateinit var presenter:SignupPresenter

    @ProvidePresenter
    fun providePresenter():SignupPresenter = scope.getInstance(SignupPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignupBinding.bind(view)

        val data = RegisterData("123","123","aa","bb")
        presenter.register(data)
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openErrorDialog(message: String, status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}