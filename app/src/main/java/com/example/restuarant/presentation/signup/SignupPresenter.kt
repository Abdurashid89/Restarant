package com.example.restuarant.presentation.signup

import com.example.restuarant.extentions.errorResponse
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.model.interactor.SignupInteractor
import com.example.restuarant.model.storage.Prefs
import com.example.restuarant.model.system.pull.FlowRouter
import com.example.restuarant.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SignupPresenter @Inject constructor(
    private val router:FlowRouter,
    private val interactor:SignupInteractor,
    private val prefs: Prefs
):BasePresenter<SignUpView>(){

    fun onBackPressed(){
        router.exit()
    }

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
                viewState.showMessage("Success")
            },{
                viewState.openErrorDialog(errorResponse(it),false)
                viewState.showMessage("Error! Try again!")
            })
    }
}