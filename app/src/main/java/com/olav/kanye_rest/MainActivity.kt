package com.olav.kanye_rest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.olav.kanye_rest.retrofit.KanyeApi
import com.olav.kanye_rest.retrofit.KanyeApiClient
import com.olav.kanye_rest.viewmodel.KanyeViewModel
import com.olav.kanye_rest.viewmodel.fetchQuotes
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // API
        val kanyeApiClient = KanyeApiClient.buildService(KanyeApi::class.java)

        val kanyeVM = KanyeViewModel(kanyeApiClient.fetchQuotes())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kanyeVM.loadQuote()

        // Connecting tvQuote to Stateflow
        lifecycleScope.launchWhenCreated {
            kanyeVM.stateFlow.collectLatest {
                if (it != null) {
                    findViewById<TextView>(R.id.tvQuote).text = it.quote
                }
            }
        }

        // Calling function once again to show multiple quotes
        findViewById<TextView>(R.id.tvQuote).setOnClickListener { kanyeVM.loadQuote() }
    }
}