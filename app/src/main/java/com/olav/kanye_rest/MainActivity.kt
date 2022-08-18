package com.olav.kanye_rest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.olav.kanye_rest.viewmodel.KanyeViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[KanyeViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadQuote()

        // Connecting tvQuote to Stateflow
        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collectLatest {
                if (it != null) {
                    findViewById<TextView>(R.id.tvQuote).text = it.quote
                }
            }
        }

        // Calling function once again to show multiple quotes
        findViewById<TextView>(R.id.tvQuote).setOnClickListener { viewModel.loadQuote() }
    }
}