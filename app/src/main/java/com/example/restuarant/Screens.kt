package com.example.restuarant

import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.restuarant.ui.login.LoginFragment
import com.example.restuarant.ui.main.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Created by shohboz on 11,Январь,2021
 */
object Screens {
    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object Login: SupportAppScreen(){
        override fun getFragment() = LoginFragment()
    }
}