package com.example.restuarant

import com.example.restuarant.ui.login.LoginFragment
import com.example.restuarant.ui.main.MainFlowFragment
import com.example.restuarant.ui.signup.SignUpFragment
import com.example.restuarant.ui.waiter.WaiterFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Created by shohboz on 11,Январь,2021
 */
object Screens {
    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object Login : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }

    object Signup : SupportAppScreen() {
        override fun getFragment() = SignUpFragment()
    }

    object Waiter : SupportAppScreen() {
        override fun getFragment() = WaiterFragment()
    }
}