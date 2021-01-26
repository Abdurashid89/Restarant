package com.example.restuarant.presentation.signup

import android.annotation.SuppressLint
import com.example.restuarant.Screens
import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.model.interactor.SignupInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
    private val router:FlowRouter,
    private val interactor:SignupInteractor,
    private val prefs: Prefs
):BasePresenter<SignUpView>(){

    fun onBackPressed(){
        router.exit()
    }

    @SuppressLint("CheckResult")
    fun register(data:RegisterData){
        viewState.makeLoadingVisible(true)
        interactor.register(data)
            .doOnSubscribe {
                viewState.makeLoadingVisible(true)
            }
            .doAfterTerminate {
                viewState.makeLoadingVisible(false)
            }
            .subscribe({
                prefs.accessToken = it.body.accessToken
                router.newChain(Screens.Login)
                viewState.showMessage("Success")
            },{
                viewState.openErrorDialog(it.errorResponse(),false)
            })
    }
}