package com.example.restuarant.di.provider

import android.content.Context
import com.example.restuarant.model.starage.Prefs
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor(
    private val context: Context,
    private val prefs: Prefs
) : Provider<OkHttpClient> {

    override fun get() = with(OkHttpClient.Builder()) {
        cache(Cache(context.cacheDir, CACHE_SIZE_BYTES))
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor {
            val requestOld = it.request()
//            if (prefs.accessToken.isNotEmpty()) {
                val request = requestOld.newBuilder()
//                    .addHeader("Authorization", "Bearer ${prefs.accessToken}")
                    .method(requestOld.method, requestOld.body)
                    .build()
                val response = it.proceed(request)
                response
            }
//        else it.proceed(requestOld)
//        }
//        if (BuildConfig.DEBUG) {
//            addInterceptor(ChuckInterceptor(context))
//            addNetworkInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//        }
        build()
    }

    companion object {
        private const val CACHE_SIZE_BYTES = 20 * 1024L
        private const val TIMEOUT = 15L
    }
}