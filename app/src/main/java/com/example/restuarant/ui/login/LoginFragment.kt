package com.example.restuarant.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.restuarant.R
import com.example.restuarant.databinding.PinLockViewBinding
import com.example.restuarant.extentions.customDialog
import com.example.restuarant.extentions.showSnackMessage
import com.example.restuarant.presentation.login.LoginPresenter
import com.example.restuarant.presentation.login.LoginView
import com.example.restuarant.ui.global.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LoginFragment : BaseFragment(), LoginView {
    override val layoutRes: Int = R.layout.pin_lock_view

    private lateinit var binding: PinLockViewBinding

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter =
        scope.getInstance(LoginPresenter::class.java)


    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = PinLockViewBinding.bind(view)


        val arrayDot = ArrayList<Boolean>()
        for(i in 0..3){
            arrayDot.add(false)
        }

        val numbers = ArrayList<String>()
        for(i in 1..9){
            numbers.add("$i")
        }
        numbers.add("0")


        val empty = R.drawable.ic_baseline_fiber_manual_white
        val fill = R.drawable.ic_baseline_fiber_manual_black

        var text_pin = ""
        for (i in 0 until binding.keypatGroup.childCount - 1) {
            binding.keypatGroup.getChildAt(i).setOnClickListener {
                binding.btnDelete.animate().setDuration(150).alpha(1f)
                for (k in 0..3) {
                    if (!arrayDot[k]) {
                        text_pin += numbers[i]
                        binding.dotGroup.getChildAt(k).setBackgroundResource(fill)
                        arrayDot[k] = true
                        break

                    }
                }
                if(text_pin.length == 4){
                    presenter.openScreen(text_pin)
//                    Toast.makeText(requireContext(), text_pin, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnDelete.setOnClickListener {

            when {
                arrayDot[3] -> {
                    binding.dotGroup.getChildAt(3).setBackgroundResource(empty)
                    arrayDot[3] = false
                    text_pin = text_pin.substring(0,3)
                    return@setOnClickListener
                }
                arrayDot[2] -> {
                    binding.dotGroup.getChildAt(2).setBackgroundResource(empty)
                    arrayDot[2] = false
                    text_pin = text_pin.substring(0,2)
                    return@setOnClickListener
                }
                arrayDot[1] -> {
                    binding.dotGroup.getChildAt(1).setBackgroundResource(empty)
                    arrayDot[1] = false
                    text_pin = text_pin.substring(0,1)
                    return@setOnClickListener
                }
                arrayDot[0] -> {
                    binding.dotGroup.getChildAt(0).setBackgroundResource(empty)
                    arrayDot[0] = false
                    text_pin = ""
                    binding.btnDelete.animate().setDuration(100).alpha(0f)
                    return@setOnClickListener
                }
            }


        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun makeLoadingVisible(status: Boolean) {
//        if (status) {
//            binding.loadingLayoutLogin.isClickable = false
//            binding.progressBarLogin.loading.visibility = View.VISIBLE
//        } else {
//            binding.loadingLayoutLogin.isClickable = true
//            binding.progressBarLogin.loading.visibility = View.GONE
//        }
    }

    override fun openErrorDialog(message: String, status: Boolean) {
        customDialog(message, status)
    }

//    override fun onDestroy() {
//        binding.root = null
//        super.onDestroy()
//    }
}