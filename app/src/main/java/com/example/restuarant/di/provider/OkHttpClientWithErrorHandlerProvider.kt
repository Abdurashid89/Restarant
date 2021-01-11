package com.example.restuarant.di.provider

import com.example.restuarant.model.interactor.ErrorResponseInterceptor
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientWithErrorHandlerProvider @Inject constructor(
    private val client: OkHttpClient
) : Provider<OkHttpClient> {

    override fun get() = client
        .newBuilder()
        .addNetworkInterceptor(ErrorResponseInterceptor())
        .build()
}