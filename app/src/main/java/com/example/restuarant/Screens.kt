package com.example.restuarant

import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.restuarant.ui.cashier.CashierFragment
import com.example.restuarant.ui.cooker.CookerFragment
import com.example.restuarant.ui.login.LoginFragment
import com.example.restuarant.ui.main.MainFlowFragment
import com.example.restuarant.ui.signup.SignUpFragment
import com.example.restuarant.ui.waiter.WaiterFragment
import com.example.restuarant.ui.wareHouse.WareHouseFragment
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

    object CookerPage: SupportAppScreen(){
        override fun getFragment() = CookerFragment()
    }

    object CashierPage : SupportAppScreen(){
        override fun getFragment() = CashierFragment()
    }
    object WarePage : SupportAppScreen(){
        override fun getFragment() = WareHouseFragment()
    }
}