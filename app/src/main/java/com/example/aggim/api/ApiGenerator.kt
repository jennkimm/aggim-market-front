package com.example.aggim.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/*
    Created by Seohyun Kim at 2021/01/21
 */

class ApiGenerator {
    fun <T> generate(api: Class<T>): T = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())
        .build()
        .create(api)

    fun <T> generateRefreshClient(api: Class<T>) : T = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(refreshClient())
            .build()
            .create(api)

    private fun refreshClient() =
            OkHttpClient.Builder().apply {
                addInterceptor(httpLoggingInterceptor())
                addInterceptor(TokenRefreshInterceptor())
            }.build()

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
            addInterceptor(ApiTokenInterceptor())
            authenticator(TokenAuthenticator())
        }.build()

    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    companion object {
        const val HOST = "http://10.0.2.2:8080"
    }
}