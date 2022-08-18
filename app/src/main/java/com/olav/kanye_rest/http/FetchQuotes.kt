package com.olav.kanye_rest.http

import com.olav.kanye_rest.model.KanyeQuote
import com.olav.kanye_rest.retrofit.KanyeApi
import java.lang.Exception

fun interface FetchQuotes {
    suspend operator fun invoke(): KanyeQuote?
}

fun KanyeApi.fetchQuotes() = FetchQuotes {
    getQuote().body() ?: throw Exception("Nope")
}