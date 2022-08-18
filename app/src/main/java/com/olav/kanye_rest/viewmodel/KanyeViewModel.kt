package com.olav.kanye_rest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olav.kanye_rest.http.FetchQuotes
import com.olav.kanye_rest.model.KanyeQuote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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