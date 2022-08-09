package com.olav.kanye_rest.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KanyeApiClient {
    val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .client(client)
        .baseUrl("https://api.kanye.rest")
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}