package com.olav.kanye_rest.viewmodel

import androidx.lifecycle.ViewModel
import com.olav.kanye_rest.model.KanyeQuote
import com.olav.kanye_rest.retrofit.KanyeApi
import com.olav.kanye_rest.retrofit.KanyeApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KanyeViewModel: ViewModel() {
    // API
    private val kanyeApiClient = KanyeApiClient.buildService(KanyeApi::class.java)
    private val call = kanyeApiClient.getQuote()

    // State management
    private var _stateFlow = MutableStateFlow<KanyeQuote?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadQuote() {
        // Clone() is to be able to reuse a Call
        call.clone().enqueue(object: Callback<KanyeQuote> {
            override fun onResponse(call: Call<KanyeQuote>, response: Response<KanyeQuote>) {
                if (response.isSuccessful) {
                    _stateFlow.value = response.body()
                }
            }

            override fun onFailure(call: Call<KanyeQuote>, t: Throwable) {
                print("Failed to fetch API.")
            }
        })
    }

}