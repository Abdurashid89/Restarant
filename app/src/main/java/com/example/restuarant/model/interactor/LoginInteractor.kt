package com.example.restuarant.model.interactor

import com.example.restuarant.model.entities.LoginData
import com.example.restuarant.model.entities.LoginResData
import com.example.restuarant.model.server.ResApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by shohboz on 11,Январь,2021
 */
class LoginInteractor @Inject constructor(
    private val api: ResApi
) {

    fun login(data: LoginData): Single<LoginResData> {
        return api.login(data)
            .map {
                it
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

}