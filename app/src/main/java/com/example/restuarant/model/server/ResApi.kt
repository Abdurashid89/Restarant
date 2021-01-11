package com.example.restuarant.model.server

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by shohboz on 11,Январь,2021
 */
interface ResApi {
    @POST("/api/auth/login")
    fun login(@Body data: String): Single<String>


}