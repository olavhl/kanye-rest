package com.olav.kanye_rest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olav.kanye_rest.model.KanyeQuote
import com.olav.kanye_rest.retrofit.KanyeApi
import com.olav.kanye_rest.retrofit.KanyeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.security.spec.ECParameterSpec

class KanyeViewModel(
    private val fetchQuotes: FetchQuotes
): ViewModel() {
    // State management
    private var _stateFlow = MutableStateFlow<KanyeQuote?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadQuote() {
        viewModelScope.launch {
            _stateFlow.value = withContext(Dispatchers.IO) {
                fetchQuotes()
            }
        }
    }
}

fun interface FetchQuotes {
    suspend operator fun invoke(): KanyeQuote?
}

fun KanyeApi.fetchQuotes() = FetchQuotes {
    getQuote().body() ?: throw Exception("Nope")
}