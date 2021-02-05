package com.example.restuarant.di.provider

import com.example.restuarant.di.ServerPath
import com.example.restuarant.model.server.ResApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class
ApiClient @Inject constructor(
        private val okHttpClient: OkHttpClient,
        @ServerPath private val serverPath: String
) : Provider<ResApi> {

    override fun get() = ApiWithChangesRegistration(getOriginalApi())

    private fun getOriginalApi() =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(serverPath)
                    .build()
                    .create(ResApi::class.java)
}