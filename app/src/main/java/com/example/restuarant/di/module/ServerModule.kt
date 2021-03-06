package com.example.restuarant.di.module

import com.example.restuarant.di.ServerPath
import com.example.restuarant.di.WithErrorHandler
import com.example.restuarant.di.provider.*
import com.example.restuarant.model.server.ResApi
import com.example.restuarant.model.storage.AppDatabase
import com.example.restuarant.model.storage.dao.UnPaidDao
import okhttp3.OkHttpClient
import org.xml.sax.ErrorHandler
import toothpick.config.Module

class ServerModule : Module() {
    init {
        // Auth
        bind(String::class.java).withName(ServerPath::class.java)
            .toInstance("http://165.22.178.228:2020")
//            .toInstance(BuildConfig.PostUrl)

        // Network
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
            .providesSingleton()
        bind(OkHttpClient::class.java).withName(WithErrorHandler::class.java).toProvider(OkHttpClientWithErrorHandlerProvider::class.java).providesSingleton()
        bind(ResApi::class.java).toProvider(ApiClient::class.java).providesSingleton()

        // Error handler with logout logic
        bind(ErrorHandler::class.java).singleton()

        bind(AppDatabase::class.java).toProvider(AppDatabaseProvider::class.java).providesSingleton()
        bind(UnPaidDao::class.java).toProvider(UnPaidDaoProvider::class.java).providesSingleton()
        //bind(ProductInDao::class.java).toProvider(ProductInProvider::class.java).providesSingleton()
    }
}