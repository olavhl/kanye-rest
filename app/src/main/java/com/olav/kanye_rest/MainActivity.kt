package com.olav.kanye_rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.olav.kanye_rest.model.KanyeQuote
import com.olav.kanye_rest.retrofit.KanyeApi
import com.olav.kanye_rest.retrofit.KanyeApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[KanyeViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadQuote()

        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collectLatest {
                if (it != null) {
                    findViewById<TextView>(R.id.tvQuote).text = it.quote
                }
            }
        }
    }
}

class KanyeViewModel: ViewModel() {
    // API
    private val kanyeApiClient = KanyeApiClient.buildService(KanyeApi::class.java)
    private val call = kanyeApiClient.getQuote()

    // State management
    private var _stateFlow = MutableStateFlow<KanyeQuote?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadQuote() {
        call.enqueue(object: Callback<KanyeQuote> {
            override fun onResponse(call: Call<KanyeQuote>, response: Response<KanyeQuote>) {
                if (response.isSuccessful) {
                    _stateFlow.value = response.body()
                }
            }

            override fun onFailure(call: Call<KanyeQuote>, t: Throwable) {
                print("Fail")
            }
        })
    }

}