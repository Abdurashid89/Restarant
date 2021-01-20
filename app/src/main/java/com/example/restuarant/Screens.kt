package com.example.restuarant

import com.example.restuarant.ui.login.LoginFragment
import com.example.restuarant.ui.main.MainFlowFragment
import com.example.restuarant.ui.signup.SignUpFragment
import com.example.restuarant.ui.wareHouse.addProduct.AddProductFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Created by shohboz on 11,Январь,2021
 */
object Screens {
    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object Login : SupportAppScreen() {
        override fun getFragment() = AddProductFragment()
    }

    object Signup : SupportAppScreen() {
        override fun getFragment() = SignUpFragment()
    }

    object AddProductPage : SupportAppScreen() {
        override fun getFragment() = AddProductFragment()
    }

}