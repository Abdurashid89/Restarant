package com.example.restuarant.ui.global

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.restuarant.R
import com.example.restuarant.di.module.FlowNavigationModule
import com.example.restuarant.extentions.setLunchScreen
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.activity_main

    private val currentFragment get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun installModules(scope: Scope) {
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java))
        )
    }

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this.activity, childFragmentManager, R.id.container) {
            override fun activityBack() {
                router.exit()
            }

            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
            ) {
                if (currentFragment != null) {
                    fragmentTransaction?.setCustomAnimations(
                        R.anim.slide_in_right_to_left,
                        R.anim.slide_out_right_to_left,
                        R.anim.slide_in_left_to_right,
                        R.anim.slide_out_left_to_right
                    )
                }
                fragmentTransaction?.setReorderingAllowed(true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toothpick.inject(this, scope)

        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLunchScreen(getLunchScreen())
        }
    }

    abstract fun getLunchScreen(): SupportAppScreen

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}