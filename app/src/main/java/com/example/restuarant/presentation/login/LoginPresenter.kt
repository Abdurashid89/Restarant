package com.example.restuarant.presentation.login

import android.os.Handler
import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.LoginData
import com.example.restuarant.model.entities.UnPaidData
import com.example.restuarant.model.interactor.LoginInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.storage.dao.UnPaidDao
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Created by shohboz on 11,Январь,2021
 */
@InjectViewState
class LoginPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: LoginInteractor,
    private val prefs: Prefs,
    private val dao: UnPaidDao
) : BasePresenter<LoginView>() {
    val list = ArrayList<UnPaidData>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        login(LoginData("+998909476154", "123"))
//        getAllUnPaidOrders()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openScreen(string: String) {

        when (string) {
            "1111" -> router.newChain(Screens.Waiter)
            "2222" -> router.newChain(Screens.CookerPage)
            "3333" -> router.newChain(Screens.CashierPage)
            "4444" -> router.newChain(Screens.WarePage)
        }
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

    private fun getAllUnPaidOrders() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 100000)

                interactor.unPaid()
                    .subscribe({
                        viewState.showMessage("Success unpiad")
                        viewState.ordersFromServer(it.objectData)
                    }, {
                        viewState.showMessage(it.errorResponse())
                    })
                    .connect()
            }
        }
        handler.post(runnable)


    }
}