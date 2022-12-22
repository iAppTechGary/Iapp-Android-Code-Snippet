package com.app.outrighttalk.network

import com.app.outrighttalk.BuildConfig
import com.google.gson.GsonBuilder

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {
    private const val CONNECT_TIME_OUT = 60L
    private const val READ_TIME_OUT = 60L
    private const val WRITE_TIME_OUT = 120L

    //Create Custom Interceptor to apply Headers application wide
    private val headerInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Accept", "application/json")
        requestBuilder.header("Content-Type", "application/json")
        chain.proceed(requestBuilder.build())
    }
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().also { interceptor ->
                        interceptor.level = HttpLoggingInterceptor.Level.BODY
                    })
                    addInterceptor(headerInterceptor)
                    addNetworkInterceptor(Interceptor { chain ->
                        val request =
                            chain.request().newBuilder().addHeader("Connection", "close")
                                .build()
                        chain.proceed(request)
                    })
                }
            }.build()
    }

    fun <T> createService(baseUrl: String, serviceType: Class<T>): T {
        val gson = GsonBuilder().disableHtmlEscaping().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.newBuilder().build())
            .build()
        return retrofit.create(serviceType)
    }
}
