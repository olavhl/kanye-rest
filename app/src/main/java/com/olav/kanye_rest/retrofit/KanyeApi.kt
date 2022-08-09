package com.olav.kanye_rest.retrofit

import com.olav.kanye_rest.model.KanyeQuote
import retrofit2.Call
import retrofit2.http.GET

interface KanyeApi {
    @GET("/")
    fun getQuote(): Call<KanyeQuote>
}