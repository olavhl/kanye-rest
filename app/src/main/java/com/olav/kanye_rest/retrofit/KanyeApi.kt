package com.olav.kanye_rest.retrofit

import com.olav.kanye_rest.model.KanyeQuote
import retrofit2.Response
import retrofit2.http.GET

interface KanyeApi {
    @GET("/")
    suspend fun getQuote(): Response<KanyeQuote>
}