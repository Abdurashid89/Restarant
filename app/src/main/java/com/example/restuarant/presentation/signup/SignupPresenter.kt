package com.example.restuarant.presentation.signup

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
}