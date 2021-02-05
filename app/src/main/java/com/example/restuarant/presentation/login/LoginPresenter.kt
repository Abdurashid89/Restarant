package com.example.restuarant.presentation.login

import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.LoginData
import com.example.restuarant.model.interactor.LoginInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by shohboz on 11,Январь,2021
 */
@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: LoginInteractor,
    private val prefs: Prefs
) : BasePresenter<LoginView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        login(LoginData("+123456789","123"))
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openScreen(string: String){

        when(string){
            "1111" ->router.newChain(Screens.Waiter)
            "2222" ->router.newChain(Screens.CookerPage)
            "3333" ->router.newChain(Screens.CashierPage)
            "4444" ->router.newChain(Screens.WarePage)
        }
    }
    fun signUpPage() {
        router.newChain(Screens.Signup)
    }


    fun login(data: LoginData) {
//        viewState.makeLoadingVisible(true)
        interactor.login(data)
            .subscribe({
                prefs.accessToken = it.body.accessToken
//                viewState.showMessage("Success")
            }, {
                viewState.openErrorDialog(it.errorResponse(), false)
//                viewState.makeLoadingVisible(false)
//                viewState.showMessage("Error, Try again!")
            }).connect()


    }
//
//    private fun getUserMe() {
//        intercepter.getUserMe()
//            .doOnSubscribe {
//                viewState.makeLoadingVisible(true)
//            }
//            .doAfterTerminate {
//                viewState.makeLoadingVisible(false)
//            }
//            .subscribe({
//                val ls = it.objectData.roles
//                prefs.fullName = "${it.objectData.firstName} ${it.objectData.lastName}"
//                prefs.phoneNUmber = it.objectData.phoneNumber
//                if (ls.size > 1) {
//                    prefs.directorOrNot = true
//                    router.newRootScreen(Screens.MainDirector)
//                } else {
//                    when (ls[0].id) {
//                        1 -> {
//                            prefs.directorOrNot = false
//                            router.newRootScreen(Screens.AdminPage)
//                        }
//                        3 -> {
//                            router.newRootScreen(Screens.SellerPage)
//                        }
//                        4 -> {
//                            router.newRootScreen(Screens.WareHousePage)
//                        }
//                    }
//                }
//            }, {
//                viewState.openErrorDialog(errorResponse(it),false)
//            }).connect()
//
//    }

}