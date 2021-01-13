package com.example.restuarant.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.visible
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

        binding.firstNameEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    binding.firstNameInput.isErrorEnabled = false
                }
            }

        })


        binding.signButton.setOnClickListener {
            val firstName = binding.firstNameEdit.text.toString().trim()
            val lastName = binding.lastNameEdit.text.toString().trim()
            val phoneNumber = binding.phoneNumberEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()

            when {
                firstName.isEmpty() -> {
                    binding.firstNameInput.error = "Error"
                }
                lastName.isEmpty() -> {
                    binding.lastNameInput.error = "Error"
                }
                phoneNumber.isEmpty() -> {
                    binding.phoneNumberInput.error = "Error"
                }
                password.isEmpty() -> {
                    binding.passwordInput.error = "Error"
                }
                else -> {
                    presenter.register(RegisterData(phoneNumber,password,firstName,lastName))
                }
            }
            return@setOnClickListener
        }

//        val data = RegisterData("123","123","aa","bb")
//        presenter.register(data)
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
            binding.progressBarRegister.loading.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}