package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.LoginResData
import com.example.restuarant.model.entities.RegisterData
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignupInteractor @Inject constructor(
    private val api:ResApi
) {
    fun register(data:RegisterData):Single<LoginResData>{
        return api.register(data).map {
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}