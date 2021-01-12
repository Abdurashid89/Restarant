package com.example.restuarant.model.server

import com.example.restuarant.model.entities.LoginData
import com.example.restuarant.model.entities.LoginResData
import com.example.restuarant.model.entities.ResponseGetUserMe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by shohboz on 11,Январь,2021
 */
interface ResApi {
    @POST("/api/auth/login")
    fun login(@Body data: LoginData): Single<LoginResData>


//    @POST
//    fun getUserMe(): Single<ResponseGetUserMe>


}