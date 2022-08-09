package com.olav.kanye_rest.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface KanyeApi {
    @GET("/")
    fun getQuote(): Call<String>
}