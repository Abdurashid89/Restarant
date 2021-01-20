package com.example.restuarant.ui.cashier

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentCashierBinding
import com.example.restuarant.databinding.FragmentSignupBinding
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.presentation.cashier.CashierPresenter
import com.example.restuarant.presentation.cashier.CashierView
import com.example.restuarant.presentation.signup.SignUpPresenter
import com.example.restuarant.presentation.signup.SignUpView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * Created by shohboz on 18,Январь,2021
 */
class CashierFragment : BaseFragment(), CashierView {
    override val layoutRes: Int = R.layout.fragment_cashier

    private lateinit var binding : FragmentCashierBinding

    @InjectPresenter
    lateinit var presenter: CashierPresenter

    @ProvidePresenter
    fun providePresenter(): CashierPresenter = scope.getInstance(CashierPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCashierBinding.bind(view)


//        binding.signButton.setOnClickListener {
//            val firstName = binding.firstNameEdit.text.toString().trim()
//            val lastName = binding.lastNameEdit.text.toString().trim()
//            val phoneNumber = binding.phoneNumberEdit.text.toString().trim()
//            val password = binding.passwordEdit.text.toString().trim()
//
//            when {
//                firstName.isEmpty() -> {
//                    binding.firstNameInput.startAnimation(
//                        AnimationUtils.loadAnimation(context, R.anim.shake)
//                    )
//                    vibrate(requireContext())
//                }
//                lastName.isEmpty() -> {
//                    binding.lastNameInput.startAnimation(
//                        AnimationUtils.loadAnimation(context, R.anim.shake)
//                    )
//                    vibrate(requireContext())
//                }
//                phoneNumber.isEmpty() -> {
//                    binding.phoneNumberInput.startAnimation(
//                        AnimationUtils.loadAnimation(context, R.anim.shake)
//                    )
//                    vibrate(requireContext())
//                }
//                password.isEmpty() -> {
//                    binding.passwordInput.startAnimation(
//                        AnimationUtils.loadAnimation(context, R.anim.shake)
//                    )
//                    vibrate(requireContext())
//                }
//                else -> {
//                    presenter.register(RegisterData(phoneNumber,password,firstName,lastName))
//                }
//            }
//            return@setOnClickListener
//        }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
//        binding.progressBarRegister.loading.visible(status)
    }

    override fun openErrorDialog(message: String, status: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }


}