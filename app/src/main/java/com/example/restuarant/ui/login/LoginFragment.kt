package com.example.restuarant.ui.login

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentLoginBinding
import com.example.restuarant.extentions.customDialog
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.extentions.vibrate
import com.example.restuarant.extentions.visible
import com.example.restuarant.model.entities.LoginData
import com.example.restuarant.presentation.login.LoginPresenter
import com.example.restuarant.presentation.login.LoginView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LoginFragment : BaseFragment(), LoginView {
    override val layoutRes: Int = R.layout.fragment_login

    private lateinit var binding: FragmentLoginBinding

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter =
        scope.getInstance(LoginPresenter::class.java)


    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        //nochanged

        binding.loginBtn.setOnClickListener {
            val phoneNumber = "+998" + binding.inputPhoneNumber.unmaskedText.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            if (phoneNumber.isEmpty() || phoneNumber.length != 13) {
                binding.inputPhoneNumber.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.inputPassword.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.shake
                    )
                )
                vibrate(requireContext())
                showSnackMessage("Parol Xato")
                return@setOnClickListener
            }
            presenter.login(LoginData(phoneNumber, password))
        }

        binding.signUp.setOnClickListener {
            presenter.signUpPage()
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun makeLoadingVisible(status: Boolean) {
        binding.loadingLayoutLogin.isClickable = !status
        binding.loadingLayoutLogin.isFocusable = !status
        binding.progressBarLogin.loading.visible(status)
    }


    override fun openErrorDialog(message: String, status: Boolean) {
        customDialog(message, status)
    }
}