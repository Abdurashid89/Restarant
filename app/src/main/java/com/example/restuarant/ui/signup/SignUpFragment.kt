package com.example.restuarant.ui.signup

import android.os.Bundle
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
import com.example.restuarant.presentation.signup.SignUpView
import com.example.restuarant.presentation.signup.SignUpPresenter
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.lang.NullPointerException

class SignUpFragment : BaseFragment(),SignUpView {
    override val layoutRes: Int = R.layout.fragment_signup

    private var _bn : FragmentSignupBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("error")

    @InjectPresenter
    lateinit var presenter:SignUpPresenter

    @ProvidePresenter
    fun providePresenter():SignUpPresenter = scope.getInstance(SignUpPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bn = FragmentSignupBinding.bind(view)


        bn.btnLogin.setOnClickListener {
            presenter.openLoginScreen()
//            val password = binding.inputPassword.text.toString().trim()
//
//            when {
//                password.isEmpty() -> {
//                    binding.inputPassword.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shake))
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
//                else -> {
////                    presenter.register(RegisterData(phoneNumber,password,firstName,lastName))
//                }
//            }

        }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
            bn.progressBarRegister.loading.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}