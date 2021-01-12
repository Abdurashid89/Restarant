package com.example.restuarant.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.restuarant.R
import com.example.restuarant.databinding.FragmentLoginBinding
import com.example.restuarant.extentions.showSnackMessage
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

//        binding.btnLogin.setOnClickListener {
//            val phoneNumber = binding.inputLogin.text.toString().trim()
//            val password = binding.inputPassword.text.toString().trim()
//            if(phoneNumber != "123"){
//                if (phoneNumber.length != 13) {
//                    binding.inputLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
//                    vibrate(requireContext())
//                    return@setOnClickListener
//                }
//            }
//            if (password.isEmpty()) {
//                binding.inputPassword.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
//                vibrate(requireContext())
//                return@setOnClickListener
//            }
//            presenter.login(LoginData(phoneNumber, password))
//        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
//        binding.loadingLayoutLogin.isClickable = !status
//        binding.progressBarLogin.loading.visible(status)
    }
    override fun openErrorDialog(message: String, status: Boolean) {
//        customDialog(message, status)
    }
}